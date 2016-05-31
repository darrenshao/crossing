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
import com.shc.crossing.specs.ParamBuilder;

public final class DemoClient {
    public static void main(String[] args){
    	CrossingClient cc = CrossingClient.getInstance();
    	cc.addServerIp("127.0.0.1");
    	cc.setPort(9000);
    	cc.setEncryptKey("miftyExampleKey");
    	cc.setDecryptKey("miftyExampleKey");
    	//cc.enableSSL();
    	cc.disableSSL();
    	cc.setSignKey("miftyExampleKey");
    	
    	//Start Crossing Client
    	try{
    		cc.startup();
    	}catch(Exception e){
    		System.exit(-1);
    	}
    	
    	MyLog.logger.info("Starting to run example calls.");

    	//LocalRpcServer for internal call testing
    	//example 1
    	JsonObject params = new JsonObject();
    	params.addProperty("name", "Shao Huicheng");
    	params.addProperty("say", "Hello, World");
    	params.addProperty("job", "Software Engineer");
    	params.addProperty("skills", "Programming,People Management etc.");
 
    	System.out.println(params.toString());
    	try{
	    	String result = cc.call("LocalRpcServer@com.shc.crossing.example.ServiceDemoCall@getSimpleReply", 
	    			params.toString(),false);
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
	    	String result = cc.call("LocalRpcServer@com.shc.crossing.example.ServiceDemoCall@doCopyMe", 
	    			pp.toString(), false);
	    	System.out.println(result);
    	}catch(CrossException ce){
    		ce.printStackTrace();
    		//
    	}
    	
    	//SharpDemoServer for NewSharp call testing
    	//example 3
    	JsonObject params1 = new JsonObject();
    	params1.addProperty("name", "Shao Huicheng");
    	params1.addProperty("say", "Hello, World");
    	params1.addProperty("job", "Software Engineer");
    	params1.addProperty("skills", "Programming,People Management etc.");
 
    	System.out.println(params1.toString());
    	try{
	    	String result = cc.call("SharpDemoServer@com.howvan.server.data.action.ServiceDemoCall@getSimpleReply", 
	    			params1.toString(),true);
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
    	
    	//example 4
    	JsonObject pp1 = new JsonObject();
    	pp1.addProperty("s1", "Are");
    	pp1.addProperty("s2", "you");
    	pp1.addProperty("s3", "ready?");

    	System.out.println(pp1.toString());
    	try{
	    	String result = cc.call("SharpDemoServer@com.howvan.server.data.action.ServiceDemoCall@doCopyMe", 
	    			pp1.toString(), false);
	    	System.out.println(result);
    	}catch(CrossException ce){
    		ce.printStackTrace();
    		//
    	}    	
    	
    	//ThriftDemoServer for Thrift-based RPC call testing
		//example 5
		JsonObject pp2 = new JsonObject();
		pp2.addProperty("echo", "How are you!");
		pp2.addProperty("name", "Darren");
    	System.out.println(pp2.toString());
    	try{
	    	String result = cc.call("ThriftDemoServer@com.howvan.mifty.service.gen.UserService@echo", 
	    			pp2.toString(), false);
	    	System.out.println(result);
    	}catch(CrossException ce){
    		ce.printStackTrace();
    		//
    	}  

		//example 6
		JsonObject pp3 = new JsonObject();
		pp3.addProperty("hello", "Good morning!");
		pp3.addProperty("name", "Darren");
    	System.out.println(pp3.toString());
    	try{
	    	String result = cc.call("ThriftDemoServer@com.howvan.mifty.service.gen.UserService@sayHello", 
	    			pp3.toString(), true);
	    	System.out.println(result);
    	}catch(CrossException ce){
    		ce.printStackTrace();
    		//
    	}      	
    
    	
    	//Stop Crossing Client
    	cc.shutdown();
    }
    
    
}
