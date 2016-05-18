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
package com.shc.crossing.example;

import com.google.gson.JsonObject;
import com.shc.crossing.client.CrossingClient;
import com.shc.crossing.exception.CrossException;
import com.shc.crossing.log.MyLog;
import com.shc.crossing.runtime.ErrorCode;

public final class DemoClient {

   
    public static void main(String[] args){
    	
    	CrossingClient cc = CrossingClient.getInstance();
    	cc.addServerIp("127.0.0.1");
    	//cc.addServerIp("192.168.0.138");
    	cc.setPort(9000);
    	cc.setEncryptKey("xxxServer@Howvan.comEncryptkey");
    	cc.setDecryptKey("xxxServer@Howvan.comEncryptkey");
    	cc.setSignKey("");
    	//cc.enableSSL();
    	cc.disableSSL();
    	cc.setSignKey("xxxServer@Howvan.comSignkey");
    	
    	//Start Crossing Client
    	try{
    		cc.startup();
    	}catch(Exception e){
    		System.exit(-1);
    	}
    	//Thread.sleep(300);
    	
    	MyLog.logger.info("Starting to run example calls.");
   	
    	//example 1
    	JsonObject params = new JsonObject();
    	params.addProperty("name", "Shao Huicheng");
    	params.addProperty("say", "Hello, World");
    	params.addProperty("job", "Software Engineer");
    	params.addProperty("skills", "Programming,People Management etc.");
    
    	System.out.println(params.toString());
    	try{
	    	String result = cc.call("LocalRpcServer", 
	    			"com.shc.crossing.example.ServiceDemoCall@getSimpleReply", 
	    			params.toString(),true);
	    	//TODO your business logics
	    	System.out.println(result);
	    	
	    	
    	}catch(CrossException e){
    		e.printStackTrace();
    		//to handle all kinds of error
    		if (e.getErrorCode() == ErrorCode.COMMON_ERR_SIGN_MISSING.getCode()){
    			//TODO
    		} 
    		else{
    			//TODO
    		}
    		
    	}
    	
    	//example 2
    	JsonObject pp = new JsonObject();
    	pp.addProperty("s1", "Are");
    	pp.addProperty("s2", "you");
    	pp.addProperty("s3", "ready?");

    	System.out.println(pp.toString());
    	
    	
    	try{
    	String result = cc.call("SharpDemoServer", 
    			"com.howvan.server.data.action.ServiceDemoCall@doCopyMe", 
    			pp.toString(), false);
    	System.out.println(result);
    	}catch(CrossException ce){
    		ce.printStackTrace();
    		//
    	}
    	
//    	result = cc.call("SharpEchoServer", 
//    			"com.ftxgame.sharp.example.EchoAction@echo", 
//    			"{'key1':'Hello'}");
//    	System.out.println(result);
//    	if (ParamBuilder.getErrorCode(result) == ErrorCode.CROSSING_ERR_UNAUTHORIZED_IP.getCode()){
//    		System.out.println("IP authorization failed, please contact the crossing administrator.");
//    	}
    	
//    	result = cc.call("SharpEchoServer", 
//    			"com.ftxgame.sharp.example.EchoAction@sum", 
//    			"{'key1':'Hello','key2':'Mami'}");
//    	System.out.println(result);
//    	
//    	result = cc.call("SharpEchoServer", 
//    			"com.ftxgame.sharp.example.EchoAction@minus", 
//    			"{'key1':'Hello','key2':'Mami'}");
//    	System.out.println(result);
    	
//    	result = cc.call("SharpHelloServer", 
//    			"com.ftxgame.sharp.example.HelloAction@hello", 
//    			"{'hello':'kitty','mama':'not at home'}");
//    	System.out.println(result);
//    	
//    	result = cc.call("SharpHelloServer", 
//    			"com.ftxgame.sharp.example.HelloAction@goodMorning", 
//    			"{'hi':'morning'}");
//    	System.out.println(result);
    	
    	//Stop Crossing Client
    	cc.shutdown();
    }
    
    
}
