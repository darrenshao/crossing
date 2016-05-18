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
package com.shc.crossing.server;

import java.net.InetSocketAddress;

import com.shc.crossing.acl.AclWizard;
import com.shc.crossing.bservice.ServiceHandler;
import com.shc.crossing.log.MyLog;
import com.shc.crossing.protobuf.CrossingReqProto;
import com.shc.crossing.protobuf.CrossingRespProto;
import com.shc.crossing.runtime.ErrorCode;
import com.shc.crossing.specs.ParamBuilder;
import com.shc.crossing.specs.ReqMsg;
import com.shc.crossing.stats.ClientCallStats;
import com.shc.crossing.stats.StatsWizard;
import com.shc.crossing.utils.Utils;
import com.shc.crossing.wizard.WizardManager;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the server.
 */
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
	private ServiceHandler shandler;
	private AclWizard aw;
	
	public ServerHandler(){
		shandler = new ServiceHandler();
		aw = (AclWizard)WizardManager.getWizard("AclWizard");
	}
	
	

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		MyLog.logger.info("Client accepted: " + ctx.channel().remoteAddress().toString());
	}



	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

	    CrossingReqProto.CrossingReq req = (CrossingReqProto.CrossingReq) msg;
	    CrossingRespProto.CrossingResp resp;
	    MyLog.logger.debug("Received Request:\n" + req.toString());
	    	
		try{   	
	    	long time_start = Utils.getTimeInMillis();
	    	
	    	ReqMsg rm = new ReqMsg();
	    	rm.seqId = req.getSeqId();
	    	rm.server = req.getServiceName();
	    	rm.inf = req.getInterfaceName();
	    	rm.params = req.getParams();
	    	rm.isEncrypt = req.getIsEncrypt();
	    	
			//Check interface access rights
	    	InetSocketAddress isa = (InetSocketAddress) ctx.channel().remoteAddress();
	    	String clientIp = Utils.getReadableIPString(isa.getAddress().getAddress());
	    	if (!aw.isIpAccessible(clientIp)){
				MyLog.logger.error("IP unauthorized: " + clientIp);
				resp = createResp(req,ParamBuilder.
						createErrorParams(ErrorCode.CROSSING_ERR_UNAUTHORIZED_IP.getCode(), 
								ErrorCode.CROSSING_ERR_UNAUTHORIZED_IP.getInfo()));
				ctx.writeAndFlush(resp);
				return;
	    	}
			if (!aw.isInterfaceAccessible(clientIp, rm.inf)){
				MyLog.logger.error("Call unauthorized: " + clientIp + " --> (" + rm.server + ")" + rm.inf);
				resp = createResp(req,ParamBuilder.
						createErrorParams(ErrorCode.CROSSING_ERR_UNAUTHORIZED_INF.getCode(), 
								ErrorCode.CROSSING_ERR_UNAUTHORIZED_INF.getInfo()));
				ctx.writeAndFlush(resp);
				return;
			}
			//MyLog.logger.info("Call authorized: " + clientIp + " --> (" + server + ")" + inf);
	    	rm.ip = clientIp;
			
	    	//do Service Call
	    	String resParams = shandler.handle(rm);
	    	resp = createResp(req, resParams);
	        ctx.writeAndFlush(resp);
	        MyLog.logger.debug("Send response:\n" + resp.toString());
	        
	        //Client side Statistics
	        long time_end = Utils.getTimeInMillis();
	        long delay = time_end - time_start;
	        ClientCallStats ccs = StatsWizard.getClientCallStats(clientIp);
	        ccs.setTimes(ccs.getTimes() + 1);
	        ccs.setMaxDelays(delay);
	        ccs.setMinDelays(delay);
	        ccs.addToTotalDelay(delay);
	        ccs.incCounterPair(rm.inf);
	        if (ParamBuilder.getErrorCode(resParams)==0){
	        	ccs.setSuccesses(ccs.getSuccesses()+1);
	        	MyLog.logger.info("Call succeeded: " + clientIp + " --> [" + rm.server + "]" + rm.inf + "(" + resParams + ")");
	        } else {
	        	ccs.setFailures(ccs.getFailures()+1);
	        	MyLog.logger.info("Call failed: " + clientIp + " --> [" + rm.server + "]" + rm.inf + "(" + resParams + ")");
	        }
	        
		}catch(Exception e){
			//
			MyLog.logger.error("Server handle error.");
			MyLog.printStackTrace(e);
			
			resp = createResp(req,ParamBuilder.
					createErrorParams(ErrorCode.CROSSING_ERR_INTERNAL.getCode(), 
							ErrorCode.CROSSING_ERR_INTERNAL.getInfo()));
			ctx.writeAndFlush(resp);
			return;
		}
    }

    
    public CrossingRespProto.CrossingResp createResp(CrossingReqProto.CrossingReq msg, String resParams){
    	CrossingRespProto.CrossingResp.Builder builder = CrossingRespProto.CrossingResp.newBuilder();
    	builder.setSeqId(msg.getSeqId());
    	builder.setServiceName(msg.getServiceName());
    	builder.setInterfaceName(msg.getInterfaceName());
    	builder.setParams(resParams);
    	return builder.build();
    }
    
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
