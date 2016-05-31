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

import java.lang.reflect.Method;
import java.util.HashMap;

import com.ftxgame.sharp.boot.SharpServer;
import com.ftxgame.sharp.container.Container;
import com.shc.crossing.exception.CrossException;
import com.shc.crossing.log.MyLog;
import com.shc.crossing.runtime.ErrorCode;

/**
 * @author shc
 *
 */
public class SharpRpcProvider extends Provider {
	private Container container;
	private HashMap<String, Object> actionMap;

	public SharpRpcProvider(boolean enabled, String clazz){
		super("SharpRpcProvider", enabled, clazz);
	}
	

	@Override
	public void init() throws CrossException {
		if (actionMap==null){
			actionMap = new HashMap<String, Object>();
		}
		
		try{
			String[] args = null;
			SharpServer.main(args);;
			container = Container.get();
		}catch(Exception e){
			MyLog.logger.error(getName() + " init failed.");
			MyLog.printStackTrace(e);
		}
	}


	@Override
	public String execute(String server, String clazz, String inf, String jsonParams, boolean encrypt) throws CrossException {
		Object jsonResult=null;
		Object action=null;
		Method[] am;
		Method mm;
		
		try{
			synchronized(actionMap){
				action = actionMap.get(clazz);
				if (action==null){
					action = container.createRemote(Class.forName(clazz), server);
					actionMap.put(clazz, action);
				}
			}
			am = action.getClass().getDeclaredMethods();
			int si=0;
			for (int i=0;i<am.length;i++){
				if (am[i].getName().equals(inf)){
					si = i;
					break;
				}
			}
			mm = action.getClass().getDeclaredMethod(am[si].getName(), am[si].getParameterTypes());
			jsonResult = mm.invoke(action, jsonParams, encrypt);
			
			MyLog.logger.info("Invoked SharpRpc call: " + server + " ==> " + clazz + " ==> " + inf);
			
		}catch(Exception e){
			MyLog.printStackTrace(e);
			throw new CrossException(ErrorCode.CROSSING_ERR_INTERNAL.getCode(),
					ErrorCode.CROSSING_ERR_INTERNAL.getInfo());
		}
		
		return jsonResult.toString();
	}
}
