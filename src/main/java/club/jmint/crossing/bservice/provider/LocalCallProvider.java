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
package club.jmint.crossing.bservice.provider;

import java.lang.reflect.Method;
import java.util.HashMap;

import club.jmint.crossing.exception.CrossException;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.runtime.ErrorCode;

/**
 * @author shc
 *
 */
public class LocalCallProvider extends Provider {
	private HashMap<String, Object> objMap;
	
	public LocalCallProvider(boolean enabled, String clazz) {
		super("LocalCallProvider", enabled, clazz);
	}

	/* (non-Javadoc)
	 * @see club.jmint.crossing.bservice.provider.Provider#init()
	 */
	@Override
	public void init() throws CrossException {
		super.init();
		if (objMap==null){
			objMap = new HashMap<String, Object>();
		}
	}

	/* (non-Javadoc)
	 * @see club.jmint.crossing.bservice.provider.Provider#execute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public String execute(String server, String clazz, String inf, String jsonParams, boolean encrypt)
			throws CrossException {
		Object jsonResult=null;
		Object execObj=null;
		Method[] am;
		Method mm;
		Class<?> cl=null;
		//long time_start,time_end;
		
		try{
			cl = Class.forName(clazz+"Impl");
		}catch(ClassNotFoundException e){
			throw new CrossException(ErrorCode.CROSSING_ERR_CLASS_NOT_FOUND.getCode(),
					ErrorCode.CROSSING_ERR_CLASS_NOT_FOUND.getInfo());
		}
		
		try{
			
			synchronized (objMap){
				execObj = objMap.get(clazz);
				if (execObj==null){
					execObj = cl.newInstance();
					objMap.put(clazz, execObj);
				}
			}
			
			am = cl.getDeclaredMethods();
			int si=0;
			for (int i=0;i<am.length;i++){
				//System.out.println(am[i]);
				if (am[i].getName().equals(inf)){
					si = i;
					break;
				}
			}
			mm = cl.getDeclaredMethod(am[si].getName(), am[si].getParameterTypes());
			jsonResult = mm.invoke(execObj, jsonParams, encrypt);

			MyLog.logger.info("Invoked LocalCall call: " + server + " ==> " + clazz + " ==> " + inf);
		}catch(Exception e){
			MyLog.printStackTrace(e);
			throw new CrossException(ErrorCode.CROSSING_ERR_INTERNAL.getCode(),
					ErrorCode.CROSSING_ERR_INTERNAL.getInfo());
		}
		
		return jsonResult.toString();		
	}
	
}
