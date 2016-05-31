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
package com.shc.crossing.bservice.provider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.shc.crossing.config.ConfigWizard;
import com.shc.crossing.config.CrossingConfig;
import com.shc.crossing.config.ServerPair;
import com.shc.crossing.exception.CrossException;
import com.shc.crossing.log.MyLog;
import com.shc.crossing.runtime.Constants;
import com.shc.crossing.runtime.ErrorCode;

/**
 * @author shc
 *
 */
public class ThriftRpcProvider extends Provider {
	//IP - protocol pair
	private HashMap<String, TProtocol> tpMap = new HashMap<String, TProtocol>();
	//service - proxy pair
	private HashMap<String, Object> proxyObjMap = new HashMap<String, Object>();
	//server - IP pair
	private HashMap<String, String> serverIpMap = new HashMap<String, String>();
	
	public ThriftRpcProvider(boolean enabled,String clazz){
		super("ThriftRpcProvider",enabled,clazz);
	}

	
	@Override
	public void init() throws CrossException {
		//Get specific configuration
		CrossingConfig cc = (CrossingConfig)ConfigWizard.getConfig(Constants.CONFIG_CROSSING);
		ArrayList<ServerPair> alsp = cc.getServers(Constants.SERVER_TYPE_THRIFTRPC);
		
		//Initialize thrift RPC transport connection
		Iterator<ServerPair> it = alsp.iterator();
		ServerPair sp = null;
		TTransport transport;
		while(it.hasNext()){
			sp = it.next();
			
			try{
				transport = new TSocket(sp.getIplist(), sp.getPort());
				TProtocol protocol = new TBinaryProtocol(transport);
				transport.open();
				tpMap.put(sp.getName(), protocol);
				
			}catch(Exception e){
				MyLog.printStackTrace(e);
				MyLog.logger.error(getName() + ": Failed to create transport to server " 
						+ sp.getName() + " by "+ sp.getIplist() + ":" + sp.getPort());
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
					mp = new TMultiplexedProtocol(tpMap.get(server), clazz);
					execObj = cl.getDeclaredConstructor(TProtocol.class).newInstance(mp);
					proxyObjMap.put(clazz, execObj);
				}
			}
			
			am = cl.getDeclaredMethods();
			int si=0;
			for (int i=0;i<am.length;i++){
				//System.out.println(am[i]);
				if (am[i].getName().equals(inf)){
					si = i;
					break;
				}
			}
			mm = cl.getDeclaredMethod(am[si].getName(), am[si].getParameterTypes());
			jsonResult = mm.invoke(execObj, jsonParams, encrypt);

			MyLog.logger.info("Invoked ThriftRpc call: " + server + " ==> " + clazz + " ==> " + inf);
		}catch(Exception e){
			MyLog.printStackTrace(e);
			throw new CrossException(ErrorCode.CROSSING_ERR_INTERNAL.getCode(),
					ErrorCode.CROSSING_ERR_INTERNAL.getInfo());
		}
		
		return jsonResult.toString();		
	}
	
	
}
