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
package com.shc.crossing.client;

import java.util.HashMap;

import com.shc.crossing.log.MyLog;
import com.shc.crossing.protobuf.CrossingRespProto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	private HashMap<String,CrossingRespProto.CrossingResp> respMsg;
	
	public ClientHandler(){
		if (respMsg==null){
			respMsg = new HashMap<String,CrossingRespProto.CrossingResp>();
		}
	}

	private void addRespMsg(CrossingRespProto.CrossingResp res){
		if (res==null) return;
		respMsg.put(String.valueOf(res.getSeqId()), res);
	}
	
	public CrossingRespProto.CrossingResp getRespMsg(String key){
		return respMsg.get(key);
	}
	
	public void removeRespMsg(String key){
		respMsg.remove(key);
	}
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	
    	CrossingRespProto.CrossingResp resp = (CrossingRespProto.CrossingResp) msg;
    	MyLog.logger.debug("Received message:\n" + resp);
    	addRespMsg(resp);
        //ctx.write(msg);
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		MyLog.logger.debug("Connection established.");
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
	
}
