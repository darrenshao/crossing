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
package club.jmint.crossing.example;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import club.jmint.crossing.client.CrossingClient;
import club.jmint.crossing.config.ClientCallConfig;
import club.jmint.crossing.config.ConfigWizard;
import club.jmint.crossing.client.ClientCallInfo;
import club.jmint.crossing.exception.CrossException;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.runtime.Constants;
import club.jmint.crossing.runtime.ErrorCode;
import club.jmint.crossing.specs.ParamBuilder;

public final class DemoClient {
    public static void main(String[] args){
    	
    	CrossingClient cc = CrossingClient.getInstance();
    	
    	ClientCallConfig ccc = new ClientCallConfig("conf/crossing_clientcall.xml");
    	ccc.loadConfig();
    	
    	cc.setClientCallInfo(ccc.getClientCallInfoMap());
    	cc.addServerIp("127.0.0.1");
    	cc.setPort(9000);
    	//cc.enableSSL();
    	cc.disableSSL();
    	
    	//Start Crossing Client
    	try{
    		cc.startup();
    	}catch(Exception e){
    		System.exit(-1);
    	}
    	
    	MyLog.logger.info("Starting to run example calls.");

    	//LocalRpcServer for internal call testing
    	//example 1
//    	JsonObject params = new JsonObject();
//    	params.addProperty("name", "Shao Huicheng");
//    	params.addProperty("say", "Hello, World");
//    	params.addProperty("job", "Software Engineer");
//    	params.addProperty("skills", "Programming,People Management etc.");
// 
//    	System.out.println(params.toString());
//    	try{
//	    	String result = cc.call("LocalRpcServer@club.jmint.crossing.example.ServiceDemoCall@getSimpleReply", 
//	    			params.toString(),false);
//	    	//TODO your business logics
//	    	System.out.println(result);
//    	}catch(CrossException e){
//    		e.printStackTrace();
//    		//to handle all kinds of error
//    		if (e.getErrorCode() == ErrorCode.COMMON_ERR_SIGN_MISSING.getCode()){
//    			//TODO
//    		} 
//    		else{
//    			//TODO
//    		}
//    	}
//    	
//    	//example 2
//    	JsonObject pp = new JsonObject();
//    	pp.addProperty("s1", "Are");
//    	pp.addProperty("s2", "you");
//    	pp.addProperty("s3", "ready?");
//
//    	System.out.println(pp.toString());
//    	try{
//	    	String result = cc.call("LocalRpcServer@club.jmint.crossing.example.ServiceDemoCall@doCopyMe", 
//	    			pp.toString(), false);
//	    	System.out.println(result);
//    	}catch(CrossException ce){
//    		ce.printStackTrace();
//    		//
//    	}
//    	
//    	//SharpDemoServer for NewSharp call testing
//    	//example 3
//    	JsonObject params1 = new JsonObject();
//    	params1.addProperty("name", "Shao Huicheng");
//    	params1.addProperty("say", "Hello, World");
//    	params1.addProperty("job", "Software Engineer");
//    	params1.addProperty("skills", "Programming,People Management etc.");
// 
//    	System.out.println(params1.toString());
//    	try{
//	    	String result = cc.call("SharpDemoServer@com.howvan.server.data.action.ServiceDemoCall@getSimpleReply", 
//	    			params1.toString(),true);
//	    	
//	    	System.out.println(result);
//    	}catch(CrossException e){
//    		e.printStackTrace();
//    		//to handle all kinds of error
//    		if (e.getErrorCode() == ErrorCode.COMMON_ERR_SIGN_MISSING.getCode()){
//    			//TODO
//    		} 
//    		else{
//    			//TODO
//    		}
//    	}
    	//TODO your business logics
//    	
//    	//example 4
//    	JsonObject pp1 = new JsonObject();
//    	pp1.addProperty("s1", "Are");
//    	pp1.addProperty("s2", "you");
//    	pp1.addProperty("s3", "ready?");
//
//    	System.out.println(pp1.toString());
//    	try{
//	    	String result = cc.call("SharpDemoServer@com.howvan.server.data.action.ServiceDemoCall@doCopyMe", 
//	    			pp1.toString(), false);
//	    	System.out.println(result);
//    	}catch(CrossException ce){
//    		ce.printStackTrace();
//    		//
//    	}    	
//    	
//    	//ThriftDemoServer for Thrift-based RPC call testing
//		//example 5
//		JsonObject pp2 = new JsonObject();
//		pp2.addProperty("echo", "How are you!");
//		pp2.addProperty("name", "Darren");
//    	System.out.println(pp2.toString());
//    	try{
//	    	String result = cc.call("ThriftDemoServer@com.howvan.mifty.service.gen.UserService@echo", 
//	    			pp2.toString(), false);
//	    	System.out.println(result);
//    	}catch(CrossException ce){
//    		ce.printStackTrace();
//    		//
//    	}  
//
		//example 6
//		JsonObject pp3 = new JsonObject();
//		pp3.addProperty("mobile", "15818657919");
//		pp3.addProperty("password", "123456");
//    	System.out.println(pp3.toString());
//    	try{
//	    	String result = cc.call("TwohalfMiftyServer@com.twohalf.mifty.service.gen.UserService@userInfoQuery", 
//	    			pp3.toString(), false);
//	    	System.out.println(result);
//    	}catch(CrossException ce){
//    		ce.printStackTrace();
//    		//
//    	}  
    	
    	
		JsonObject pp4 = new JsonObject();
		//pp4.addProperty("true_name", "shaohuicheng");
		//pp4.addProperty("is_login", 1);
		pp4.addProperty("user_id", "1000000005");
		pp4.addProperty("user_name", "shaohuicheng");
		pp4.addProperty("product_id", "1000000008");
		pp4.addProperty("product_name", "1000000008");
		pp4.addProperty("fields", "user_id,user_name,status,send_status,pay_status,product_id,product_name");
		pp4.addProperty("id", "1000000002");
    	System.out.println(pp4.toString());
    	try{
	    	String result = cc.call("TwohalfMiftyServer@com.twohalf.mifty.service.gen.OrderService@orderInfoQuery", 
	    			pp4.toString(), false);
	    	System.out.println(result);
    	}catch(CrossException ce){
    		ce.printStackTrace();
    		//
    	}
    	
     	
    	HashMap<String, String> test = new HashMap<String, String>();
    	test.put("key1", "value1");
    	testMap(test);
    	System.out.println(test.get("key1"));
    	System.out.println(test.get("testkey1"));
    	
    	
    	//Stop Crossing Client
    	cc.shutdown();
    }
    
    public static void testMap(HashMap<String, String> params){
    	params.put("testkey1", "testvalue1");
    }
}
