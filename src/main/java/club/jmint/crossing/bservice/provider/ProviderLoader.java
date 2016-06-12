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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;

import club.jmint.crossing.config.ConfigWizard;
import club.jmint.crossing.config.CrossingConfig;
import club.jmint.crossing.config.ProviderPair;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.runtime.Constants;

/**
 * @author shc
 *
 */
public class ProviderLoader {

	public static void load(ProviderWizard providerWizard) {
		//providerWizard.add("SharpRpcProvider", new SharpRpcProvider());
		//providerWizard.add("ThriftRpcProvider", new ThriftRpcProvider());
		CrossingConfig cc = (CrossingConfig)ConfigWizard.getConfig(Constants.CONFIG_CROSSING);
		ArrayList<ProviderPair> al = cc.getProviders();
		Iterator<ProviderPair> it = al.iterator();
		ProviderPair pp;
		while(it.hasNext()){
			pp = it.next();
			if (pp.isEnabled()){
				//this Provider enable, then load it
				
				try {
					Class<?> cl = Class.forName(pp.getClazz());
					Constructor<?> co = cl.getDeclaredConstructor(boolean.class,String.class);
					Object[] params = new Object[2];
					params[0] = Boolean.valueOf(pp.isEnabled());
					params[1] = pp.getClazz();
					providerWizard.add(pp.getName(), (Provider)co.newInstance(params));
					MyLog.logger.info(pp.getName()+"("+pp.getClazz()+ ") loaded.");
					//System.out.println(providerWizard.getProvider(name));
				} catch (Exception e) {
					MyLog.printStackTrace(e);
				} 
				
			}
		}
	}

}
