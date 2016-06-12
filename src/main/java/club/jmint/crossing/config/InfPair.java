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

/**
 * @author shc
 *
 */
public class InfPair {
	private String infName;
	private Boolean isEncrypt;	
	
	public InfPair(String infName, Boolean isEncrypt) {
		this.infName = infName;
		this.isEncrypt = isEncrypt;
	}
	
	/**
	 * @return the infName
	 */
	public String getInfName() {
		return infName;
	}
	/**
	 * @param infName the infName to set
	 */
	public void setInfName(String infName) {
		this.infName = infName;
	}
	/**
	 * @return the isEncrypt
	 */
	public Boolean getIsEncrypt() {
		return isEncrypt;
	}
	/**
	 * @param isEncrypt the isEncrypt to set
	 */
	public void setIsEncrypt(Boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

}
