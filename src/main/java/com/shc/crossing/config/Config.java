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
package com.shc.crossing.config;

import org.apache.commons.configuration.*;

import com.shc.crossing.log.MyLog;

/**
 * @author shc
 *
 */
public class Config implements IConfig{
	protected String name;
	protected String configFilePath;
	
	public Config(String name, String filePath){	
		this.name = name;
		this.configFilePath = filePath;
	}

	
	public static PropertiesConfiguration loadPropertiesConfigFile(String file){
		
		PropertiesConfiguration config=null;
		try {
			//String fileName = Constants.DIR_CONF+File.separator+file;
			config = new PropertiesConfiguration(file);
			//System.out.println(config);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			MyLog.printStackTrace(e);
		}
		
		return config;
	}
	
	public static XMLConfiguration loadXMLConfigFile(String file){
		XMLConfiguration config=null;
		try {
			//String fileName = Constants.DIR_CONF+File.separator+file;
			config = new XMLConfiguration(file);
		} catch (ConfigurationException e){
			MyLog.printStackTrace(e);
		}
		
		return config;
	}

	public Config loadConfig() {
		MyLog.logger.debug("loading " + name);
		return null;
	}

	public void print(){
		
	}
}
