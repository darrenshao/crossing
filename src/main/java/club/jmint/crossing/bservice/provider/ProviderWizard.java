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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import club.jmint.crossing.exception.CrossException;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.wizard.Wizard;

/**
 * @author shc
 *
 */
public class ProviderWizard extends Wizard {
	private static HashMap<String, Provider> providers = null;

	public ProviderWizard(String name){
		super(name);
	}
	
	public static Provider getProvider(String name){
		return providers.get(name);
	}
	
	public void add(String name, Provider p){
		providers.put(name, p);
	}
	
	public void remove(String name){
		providers.remove(name);
	}
	
	/* (non-Javadoc)
	 * @see io.wizard.IWizard#init()
	 */
	@Override
	public void init() {
		super.init();
		
		if (providers==null){
			providers = new HashMap<String, Provider>();
		}
		
		MyLog.logger.info("Starting load service providers.");
		ProviderLoader.load(this);
	
		initProvider();
	}
	
	private void initProvider(){
		Iterator<Entry<String, Provider>> it = providers.entrySet().iterator();
		Entry<String, Provider> en;
		Provider p;
		while(it.hasNext()){
			en = it.next();
			try {
				p = en.getValue();
				p.init();
				MyLog.logger.info(p.getName() + " init.");
			} catch (CrossException e) {
				MyLog.printStackTrace(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see io.wizard.IWizard#startup()
	 */
	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see io.wizard.IWizard#shutdown()
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
