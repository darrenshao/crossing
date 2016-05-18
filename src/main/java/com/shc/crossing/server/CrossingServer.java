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

import com.shc.crossing.config.ConfigWizard;
import com.shc.crossing.config.ServerConfig;
import com.shc.crossing.log.MyLog;
import com.shc.crossing.runtime.Constants;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;



/**
 * @author shc
 *
 */
public class CrossingServer{
	private String address;
	private int port;
	private int backlog;
	private boolean ssl;
	private SslContext sslCtx;
	private CrossingServerState state = CrossingServerState.STATE_UNINIT;
	
	enum CrossingServerState{STATE_UNINIT,STATE_INIT,STATE_RUN,STATE_SHUTDOWN};
	
	public CrossingServer(){
	}

	public void start(){
		//load configuration
		//ConfigWizard cw = (ConfigWizard)WizardManager.getWizard("ConfigWizard");
		ServerConfig config = (ServerConfig)ConfigWizard.getConfig(Constants.CONFIG_SERVER);
		this.address = config.getItem("server.bind_address");
		this.port = Integer.parseInt(config.getItem("server.port"));
		this.backlog = Integer.parseInt(config.getItem("server.backlog"));
		this.ssl = Boolean.parseBoolean(config.getItem("server.ssl"));
		
		//Configure SSL
        if (ssl) {
        	try {
	            SelfSignedCertificate ssc = new SelfSignedCertificate();
	            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        	} catch(Exception e){
        		MyLog.printStackTrace(e);
        	}
        } else {
            sslCtx = null;
        }

        //Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, backlog)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ServerChannelInitializer(sslCtx));

            // Start the server.
            ChannelFuture f = b.bind(port).sync();
            MyLog.logger.info("Crossing Server started......");

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
            
        } catch (InterruptedException e) {
        	MyLog.printStackTrace(e);
		} finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            MyLog.logger.info("Crossing Server shutdown......");
        }
	}

	
}
