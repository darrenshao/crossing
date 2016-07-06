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
package club.jmint.crossing.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import club.jmint.crossing.client.call.CrossingCall;
import club.jmint.crossing.client.config.ClientCallInfo;
import club.jmint.crossing.client.config.ClientConfig;
import club.jmint.crossing.runtime.Constants;
import club.jmint.crossing.runtime.ErrorCode;
import club.jmint.crossing.specs.CrossException;
import club.jmint.crossing.utils.CrossLog;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;


/**
 * @author shc
 *
 */
public class CrossingClient{
	private ClientConfig config = null;
	private final static CrossingClient cc = new CrossingClient();
	private String ip = null;
	private boolean isSSLEnabled = false;
	private int port = Constants.SERVER_PORT;
	private ChannelFuture cf = null;
	private Channel channel = null;
	private EventLoopGroup group = null;
	private Bootstrap bs = null;
	private CrossingCall ccall = null;
	private ClientHandler chandler = null;
		
	public static CrossingClient getInstance(){
		return cc;
	}
	
	private CrossingClient(){
		init();
	}

	public void init() {
		//load configuration from file: crossing_client.xml
		config = new ClientConfig("conf/crossing_client.xml");
		ip = config.getCrossingServer().ip;
		port = Integer.parseInt(config.getCrossingServer().port);

		chandler = new ClientHandler();
	}
	
	public void startup() throws CrossException{
        SslContext sslCtx = null;
        if (isSSLEnabled) {
        	try{
            sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        	}catch(Exception e){
        		CrossLog.printStackTrace(e);
        		throw new CrossException(ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getCode(),
        				ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getInfo());
        	}
        } else {
            sslCtx = null;
        }

        // Configure the client.
        group = new NioEventLoopGroup();
        try {
            bs = new Bootstrap();
            bs.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ClientChannelInitializer(sslCtx,chandler));

            
            // Start the client.
            cf = bs.connect(ip, port).sync();
            channel = cf.channel();
            
            // Wait until the connection is closed.
            //f.channel().closeFuture().sync();
        }catch(Exception e){
        	CrossLog.printStackTrace(e);
    		throw new CrossException(ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getCode(),
    				ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getInfo());

        	//System.exit(-1);;
        }finally{
	        // Shut down the event loop to terminate all threads.
	        //group.shutdownGracefully();
		}
        
        
        ccall = new CrossingCall(cf, chandler, config);
        CrossLog.logger.info("Crossing Client started.");
	}

	public void shutdown() {
		if (channel!=null){
			channel.disconnect();
			channel.close();
		}
		if (group!=null){
			group.shutdownGracefully();
		}
		CrossLog.logger.info("Crossing Client stopped.");
	}
	
	public String call(String inf, String params) throws CrossException{
		return ccall.doSyncCall(inf, params);
	}
	
	public String call(String inf, String params, boolean isEncrypt) throws CrossException{
		return ccall.doSyncCall(inf, params, isEncrypt);
	}
	
	public CallResult serviceCall(String inf, JsonObject params, boolean isEncrypt){
    	String result = null;
    	CallResult cr = new CallResult();
    	try{
    		if (isEncrypt){
    			result = call(inf, params.toString(), true);
    		}else{
    			result = call(inf, params.toString());
    		}
    	}catch(CrossException e){
    		CrossLog.printStackTrace(e);
    		CrossLog.logger.error("service call failed.");
    		return null;
    	}    	
    	
		JsonParser jp = new JsonParser();
		JsonObject jo;
		try{
			jo = (JsonObject)jp.parse(result);
		}catch(JsonSyntaxException e){
    		CrossLog.printStackTrace(e);
    		CrossLog.logger.error("service call failed.");
    		return null;
		}
		
		cr.errorCode = jo.getAsJsonPrimitive("errorCode").getAsInt();
		if (cr.errorCode==ErrorCode.SUCCESS.getCode()){
			cr.isSuccess = true;
		} else {
			cr.isSuccess = false;
		}
		cr.errorInfo = jo.getAsJsonPrimitive("errorInfo").getAsString();
		if (jo.has("params")){
			cr.data = jo.getAsJsonObject("params");
		}
		
		return cr;
	}
	
}
