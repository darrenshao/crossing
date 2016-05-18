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
package com.shc.crossing.acl;

import com.shc.crossing.config.AclConfig;

/**
 * @author shc
 *
 */
public class Acl {
	private AclConfig aclconf;
	
	public Acl(AclConfig config){
		this.aclconf = config;
	}
	
	/**
	 * check if the client IP address is authenticated.
	 */
	public boolean isIpAccessible(String clientIp){
		return aclconf.isIpAccessible(clientIp);
	}
	
	/**
	 * check if the client have the rights to access given interface
	 * @return
	 */
	public boolean isInterfaceAccessible(String clientIp, String inf){
		return aclconf.isInterfaceAccessible(clientIp, inf);
	}
}
