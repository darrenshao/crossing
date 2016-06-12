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

public class ServerPair {
	private String name;
	private String type;
	private String iplist;
	private int port;
	private String methodProxy;
	
	public ServerPair(){
		
	}
	
	public ServerPair(String name, String type, String methodProxy, String iplist, int port) {
		super();
		this.name = name;
		this.type = type;
		this.methodProxy = methodProxy;
		this.iplist = iplist;
		this.port = port;
	}
	
	
	
	public String getMethodProxy() {
		return methodProxy;
	}

	public void setMethodProxy(String methodProxy) {
		this.methodProxy = methodProxy;
	}

	public void setPort(int port){
		this.port = port;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIplist() {
		return iplist;
	}
	public void setIplist(String iplist) {
		this.iplist = iplist;
	}
	
	
}
