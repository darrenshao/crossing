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
package club.jmint.crossing.config;

import club.jmint.crossing.client.config.ClientConfig;
import club.jmint.crossing.runtime.Constants;
import club.jmint.crossing.utils.CrossLog;
import club.jmint.crossing.wizard.Wizard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.configuration.*;

/**
 * @author shc
 *
 */
public class ConfigWizard extends Wizard {
	private static HashMap<String, Config> confMap = new HashMap<String, Config>();

	public ConfigWizard(String name){
		super(name);
	}
	
	public static Config getConfig(String type){
		return confMap.get(type);
	}
	
	public static Configuration loadPropertiesConfigFile(String file){
		
		Configuration config=null;
		try {
			config = new PropertiesConfiguration(file);
		} catch (ConfigurationException e) {
			CrossLog.printStackTrace(e);
		}
		
		return config;
	}
	
	public static Configuration loadXMLConfigFile(String file){
		Configuration config=null;
		try {
			config = new XMLConfiguration(file);
		} catch (ConfigurationException e){
			CrossLog.printStackTrace(e);
		}
		
		return config;
	}
	
	private void loadConfig(){
		Iterator<Entry<String, Config>> it = confMap.entrySet().iterator();
		Entry<String, Config> en;
		while(it.hasNext()){
			en = it.next();
			en.getValue().loadConfig();
			en.getValue().print();
		}
	}
	
	/* (non-Javadoc)
	 * @see io.wizard.Wizard#init()
	 */
	@Override
	public void init() {
		super.init();
		if (confMap==null){
			confMap = new HashMap<String, Config>();
		}
		confMap.put(Constants.CONFIG_SERVER, new ServerConfig("conf/server.properties"));
		confMap.put(Constants.CONFIG_ACL, new AclConfig("conf/acl.xml"));
		confMap.put(Constants.CONFIG_CROSSING, new CrossingConfig("conf/crossing.xml"));
		loadConfig();
	}

	/* (non-Javadoc)
	 * @see io.wizard.Wizard#startup()
	 */
	@Override
	public void startup() {
		// TODO Auto-generated method stub
		super.startup();
	}

	/* (non-Javadoc)
	 * @see io.wizard.Wizard#shutdown()
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		super.shutdown();
	}

	
}
