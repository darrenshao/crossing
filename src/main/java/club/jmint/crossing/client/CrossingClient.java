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

import club.jmint.crossing.client.call.CrossingCall;
import club.jmint.crossing.config.ClientCallConfig;
import club.jmint.crossing.config.ConfigWizard;
import club.jmint.crossing.exception.CrossException;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.runtime.Constants;
import club.jmint.crossing.runtime.ErrorCode;
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
	private final static CrossingClient cc = new CrossingClient();
	private ArrayList<String> ips = null;
	private boolean isSSLEnabled = false;
	private int port = Constants.SERVER_PORT;
	private ChannelFuture cf = null;
	private Channel channel = null;
	private EventLoopGroup group = null;
	private Bootstrap bs = null;
	private CrossingCall ccall = null;
	private ClientHandler chandler = null;
	private HashMap<String, ClientCallInfo> ccimap = null;

	public void setClientCallInfo(HashMap<String, ClientCallInfo> ccim){
		this.ccimap = ccim;

    	if (ccimap.get("DEFAULT")==null){//no configuration file or no item found.
    		ccimap.put("DEFAULT", new ClientCallInfo("", "DEFAULT", "", "MD5", "miftyExampleKey", 
    				"DES", "miftyExampleKey", "miftyExampleKey"));
    	}
	}
	
	public void addServerIp(String ip){
		ips.add(ip);
	}
	
	public void removeServerIp(String ip){
		ips.remove(ip);
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public void enableSSL(){
		this.isSSLEnabled = true;
	}
	
	public void disableSSL(){
		this.isSSLEnabled = false;
	}
		
	public static CrossingClient getInstance(){
		return cc;
	}
	
	private CrossingClient(){
		init();
	}

	public void init() {
		if (ips==null){
			ips = new ArrayList<String>();
		}
		chandler = new ClientHandler();
	}
	
	public void startup() throws CrossException{
        // Configure SSL
        SslContext sslCtx = null;
        if (isSSLEnabled) {
        	try{
            sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        	}catch(Exception e){
        		MyLog.printStackTrace(e);
        		throw new CrossException(ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getCode(),
        				ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getInfo());
        		//System.exit(-1);;
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
            cf = bs.connect(ips.get(0), port).sync();
            channel = cf.channel();
            
            // Wait until the connection is closed.
            //f.channel().closeFuture().sync();
        }catch(Exception e){
        	MyLog.printStackTrace(e);
    		throw new CrossException(ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getCode(),
    				ErrorCode.CROSSING_ERR_CLIENT_STARTUP.getInfo());

        	//System.exit(-1);;
        }finally{
	        // Shut down the event loop to terminate all threads.
	        //group.shutdownGracefully();
		}
        
        
        ccall = new CrossingCall(cf, chandler, ccimap);
        MyLog.logger.info("Crossing Client started.");
	}

	public void shutdown() {
		if (channel!=null){
			channel.disconnect();
			channel.close();
		}
		if (group!=null){
			group.shutdownGracefully();
		}
		MyLog.logger.info("Crossing Client stopped.");
	}
	
	public String call(String inf, String params) throws CrossException{
		return ccall.doSyncCall(inf, params);
	}
	
	public String call(String inf, String params, boolean isEncrypt) throws CrossException{
		return ccall.doSyncCall(inf, params, isEncrypt);
	}
}
