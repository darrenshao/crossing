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

import club.jmint.crossing.specs.CrossException;

/**
 * @author shc
 *
 */
public abstract class Provider implements IProvider {
	private String name;
	private boolean enabled;
	private String clazz;
	
	protected Provider(boolean enabled, String clazz){
		this.name = "Provider";
		this.enabled = enabled;
		this.clazz = clazz;
	}
	
	protected Provider(String name, boolean enabled, String clazz){
		this.name = name;
		this.enabled = enabled;
		this.clazz = clazz;
	}


	/* (non-Javadoc)
	 * @see club.jmint.crossing.bservice.provider.IProvider#init()
	 */
	public void init() throws CrossException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see club.jmint.crossing.bservice.provider.IProvider#execute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public String execute(String server, String clazz, String inf, String jsonParams, boolean encrypt)
			throws CrossException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see club.jmint.crossing.bservice.provider.IProvider#getName()
	 */
	public String getName(){
		// TODO Auto-generated method stub
		return name;
	}

	/* (non-Javadoc)
	 * @see club.jmint.crossing.bservice.provider.IProvider#getClazz()
	 */
	public String getClazz() {
		// TODO Auto-generated method stub
		return clazz;
	}

	/* (non-Javadoc)
	 * @see club.jmint.crossing.bservice.provider.IProvider#isEnabled()
	 */
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
	
}
