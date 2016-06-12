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
package club.jmint.crossing.client.call;

import java.net.InetSocketAddress;
import java.util.HashMap;

import club.jmint.crossing.client.ClientCallInfo;
import club.jmint.crossing.client.ClientHandler;
import club.jmint.crossing.exception.CrossException;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.protobuf.CrossingReqProto;
import club.jmint.crossing.protobuf.CrossingRespProto;
import club.jmint.crossing.specs.ParamBuilder;
import club.jmint.crossing.utils.Utils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author shc
 *
 */
public class CrossingCall implements ICall {
	private HashMap<String, ClientCallInfo> ccimap = null;
	//private boolean encrypt = false;
	private ChannelFuture cfuture;
	private ClientHandler handler;
	private String currentInf;
	private ClientCallInfo cci;
	
	public CrossingCall(ChannelFuture cf, ClientHandler ch){
		this.cfuture = cf;
		this.handler = ch;
		init();
	}
	
	public CrossingCall(ChannelFuture cf, ClientHandler ch, HashMap<String, ClientCallInfo> ccim){
		this.cfuture = cf;
		this.handler = ch;
		this.ccimap = ccim;
		init();
	}
	
	private synchronized void init(){
	}
	
	private String getCciByInf(String inf) {
		this.currentInf = inf;
		String[] el = currentInf.split("@");//inf format: xxx@yyy@zzz
		this.cci = ccimap.get(el[1]);
		if (cci==null){
			cci = ccimap.get("DEFAULT");
		}
		return el[1];
	}
	
	private String getSignedParams(String p) throws CrossException{
		String signed=ParamBuilder.buildSignedParams(p,cci.signKey);
		
		MyLog.logger.debug("Signed Params: " + signed);
		
		return signed;
	}
	
	private String getEncryptedParams(String signedParams) throws CrossException{
		String encrypted=ParamBuilder.buildEncryptedParams(signedParams,cci.encryptKey);
		
		MyLog.logger.debug("Encrypted Params: " + encrypted);

		return encrypted;
	}
	
	private String checkSignAndRemove(String p) throws CrossException{
		MyLog.logger.debug("Response signed params: " + p);
		String rp = ParamBuilder.checkSignAndRemove(p,cci.signKey);
		return rp;
	}
	
	private String getDecryptedParams(String encryptParams) throws CrossException{
		String dep = ParamBuilder.buildDecryptedParams(encryptParams,cci.decryptKey);
		return dep;
	}
	
	public String doSyncCall(String inf, String params) throws CrossException{
		//verify inf format and params format
		ParamBuilder.checkCallInfFormat(inf);
		ParamBuilder.checkCallParamFormat(params);
		
		getCciByInf(inf);
		
		String signedp = getSignedParams(params);
		MyLog.logger.info("Invoked call: " + inf);
		String response = syncCall(inf, signedp, false);
		
		//
		String nosign = checkSignAndRemove(response);
		return nosign;
	}
	
	public String doSyncCall(String inf, String params, boolean isEncrypt) throws CrossException{
		//verify inf format and params format
		ParamBuilder.checkCallInfFormat(inf);
		ParamBuilder.checkCallParamFormat(params);
		
		if (!isEncrypt){
			return doSyncCall(inf, params);
		}
		
		getCciByInf(inf);
		
		String signedp = getSignedParams(params);
		String encryptedp = getEncryptedParams(signedp);
		MyLog.logger.info("Invoked call(*): " + inf);
		String response = syncCall(inf, encryptedp, isEncrypt);
		
		//
		String decryptedres = getDecryptedParams(response);
		String nosignret = checkSignAndRemove(decryptedres);
		
		return nosignret;
	}
		

	/* (non-Javadoc)
	 * @see club.jmint.crossing.call.ICall#syncCall(java.lang.String, java.lang.String, boolean)
	 */
	public String syncCall(String inf, String params, boolean isEncrypt) throws CrossException{
		//Starting client call
		Channel channel = cfuture.channel();
		CrossingReqProto.CrossingReq.Builder builder = CrossingReqProto.CrossingReq.newBuilder();
		
		InetSocketAddress isa = (InetSocketAddress)channel.localAddress();
		String ip = isa.getAddress().getHostAddress();
	
		builder.setSeqId(Utils.getSeqId(ip));
		builder.setInterfaceName(inf);
		builder.setParams(params);
		builder.setIsEncrypt(isEncrypt);
		
		//sending a call
		CrossingReqProto.CrossingReq callmsg = builder.build();
		
		MyLog.logger.debug("Sending a request:\n" + callmsg);
		long stime = Utils.getTimeInMillis();
		
		channel.writeAndFlush(callmsg);
		
		//wait the result
		CrossingRespProto.CrossingResp res = null;
		String msgKey = String.valueOf(callmsg.getSeqId());
		try {
			do { 
				cfuture.await();
				res=handler.getRespMsg(msgKey);
			} while (res==null);
		} catch (InterruptedException e) {
			MyLog.printStackTrace(e);
		}
		long etime = Utils.getTimeInMillis();
		long delay = etime - stime;
		MyLog.logger.debug(String.format("Received a response(in %dms):\n",delay) + res);
		
		//output the calling result
		//String rs = res.getServiceName();
		//String ri = res.getInterfaceName();
		String rparams = res.getParams();

		//remove the respMsg after getting return.
		handler.removeRespMsg(msgKey);
		
		return rparams;
	}

}
