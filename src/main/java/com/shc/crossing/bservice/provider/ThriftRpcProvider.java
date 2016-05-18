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

import com.shc.crossing.exception.CrossException;
import com.shc.crossing.log.MyLog;

/**
 * @author shc
 *
 */
public class ThriftRpcProvider extends Provider {
	
	public ThriftRpcProvider(boolean enabled,String clazz){
		super("ThriftRpcProvider",enabled,clazz);
	}

	
	@Override
	public void init() throws CrossException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String execute(String server, String clazz, String inf, String jsonParams, boolean encrypt) throws CrossException {
		// TODO Auto-generated method stub
		Object jsonResult;
		jsonResult = "{\"errorCode\":0,\"Hello\":\"Goodbye\"}";
		//not supported temporarily.
		
		MyLog.logger.info("Invoked ThriftRpc call: " + server + " ==> "+ clazz + " " + inf);

		return jsonResult.toString();
	}

}