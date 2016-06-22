/*
 * Copyright 2016 The Crossing Project
 *
 * The Crossing Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package club.jmint.crossing.bservice.provider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import club.jmint.crossing.config.ConfigWizard;
import club.jmint.crossing.config.CrossingConfig;
import club.jmint.crossing.config.ServerPair;
import club.jmint.crossing.exception.CrossException;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.runtime.Constants;
import club.jmint.crossing.runtime.ErrorCode;

/**
 * @author shc
 *
 */
public class ThriftRpcProvider extends Provider {
	private HashMap<String, TransportClient> tpMap = new HashMap<String, TransportClient>();
	private HashMap<String, Object> proxyObjMap = new HashMap<String, Object>();
	private HashMap<String, String> callProxyMap = new HashMap<String, String>();
	private String defaultCallProxy = "callProxy";
	
	public ThriftRpcProvider(boolean enabled,String clazz){
		super("ThriftRpcProvider",enabled,clazz);
	}

	public HashMap<String, TransportClient> getTransportClients(){
		return tpMap;
	}
	
	@Override
	public void init() throws CrossException {
		//Get specific configuration
		CrossingConfig cc = (CrossingConfig)ConfigWizard.getConfig(Constants.CONFIG_CROSSING);
		ArrayList<ServerPair> alsp = cc.getServers(Constants.SERVER_TYPE_THRIFTRPC);
		
		//Initialize thrift RPC transport connection
		Iterator<ServerPair> it = alsp.iterator();
		ServerPair sp = null;
		TransportClient tc;
		String cp = null;
		while(it.hasNext()){
			sp = it.next();
			
			cp = sp.getMethodProxy();
			callProxyMap.put(sp.getName(), ((cp==null)||cp.isEmpty())?defaultCallProxy:cp);
			
			tc = new TransportClient();
			tc.transport = new TSocket(sp.getIplist(), sp.getPort());
			tc.serverIp = sp.getIplist();
			tc.serverPort = sp.getPort();
			tc.serverName = sp.getName();
			tc.protocol = new TBinaryProtocol(tc.transport);
			tpMap.put(sp.getName(), tc);
			
			//establish connection to Thrift server
			try {
				tc.transport.open();
			} catch (TTransportException e) {
				MyLog.printStackTrace(e);
				MyLog.logger.error(getName() + ": Failed to create transport to server " 
						+ sp.getName() + " by "+ sp.getIplist() + ":" + sp.getPort());
				//if failed, drop it to the list for reopen.
				tc.isConnected = false;
				continue;
			}
			MyLog.logger.info(getName() + ": Succeeded to create transport to server " 
						+ sp.getName() + " by "+ sp.getIplist() + ":" + sp.getPort());	
		}
	}

	@Override
	public String execute(String server, String clazz, String inf, String jsonParams, boolean encrypt) throws CrossException {
		Object jsonResult=null;
		Object execObj=null;
		Method[] am;
		Method mm;
		Class<?> cl=null;
		TMultiplexedProtocol mp = null;
		
		String proxyClassName = clazz + "$Client";
		try{
			cl = Class.forName(proxyClassName);
		}catch(ClassNotFoundException e){
			throw new CrossException(ErrorCode.CROSSING_ERR_CLASS_NOT_FOUND.getCode(),
					ErrorCode.CROSSING_ERR_CLASS_NOT_FOUND.getInfo());
		}
		
		try{
			synchronized (proxyObjMap){
				execObj = proxyObjMap.get(clazz);
				if (execObj==null){
					mp = new TMultiplexedProtocol(tpMap.get(server).protocol, clazz);
					execObj = cl.getDeclaredConstructor(TProtocol.class).newInstance(mp);
					proxyObjMap.put(clazz, execObj);
				}
			}
			
			am = cl.getDeclaredMethods();
			int si=0;
			for (int i=0;i<am.length;i++){	//should be optimized later.
				//System.out.println(am[i]);
				if (am[i].getName().equals(callProxyMap.get(server))){
					si = i;
					break;
				}
			}
			mm = cl.getDeclaredMethod(am[si].getName(), am[si].getParameterTypes());
			jsonResult = mm.invoke(execObj, inf, jsonParams, encrypt);

			MyLog.logger.info("Invoked ThriftRpc call: " + server + " ==> " + clazz + " ==> " + inf);
		}catch(Exception e){
			MyLog.printStackTrace(e);
			throw new CrossException(ErrorCode.CROSSING_ERR_INTERNAL.getCode(),
					ErrorCode.CROSSING_ERR_INTERNAL.getInfo());
		}
		
		return jsonResult.toString();		
	}
	
	
}
