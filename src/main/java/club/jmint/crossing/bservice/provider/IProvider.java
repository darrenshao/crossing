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
public interface IProvider {
	
	public void init() throws CrossException;
	public String execute(String server, String clazz, String inf, String jsonParams, boolean encrypt) throws CrossException;
	public String getName();
	public String getClazz();
	public boolean isEnabled();
}
