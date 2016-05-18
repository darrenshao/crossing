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
package com.shc.crossing.wizard;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shc
 *
 */
public class Singletion<T> {
	private static final ConcurrentHashMap<Class, Object> smap= new ConcurrentHashMap<Class, Object>();
	
	public static <T>T getInstance(Class<T> type){
        Object ob = smap.get(type);
        try {
            if(ob==null){
                synchronized (smap) {
                	ob = type.newInstance();
                	smap.put(type,ob);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T)ob;
	}
}
