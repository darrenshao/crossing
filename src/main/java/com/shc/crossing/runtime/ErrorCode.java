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
package com.shc.crossing.runtime;

/**
 * @author shc
 *
 */
public enum ErrorCode {
	/**
	 * All runtime error codes defined here,
	 * 0x00 00 0000 - 0xFF FF FFFF
	 * first 2 digits: used for server type
	 * following 2 digits: used for service/module/subsystem 
	 * last 4 digits: used for detailed errors
	 */
		//Success
		SUCCESS(0x00000000,"Success"),
	
		//Crossing ErrorCode： 0x01 00 0000 - 0x01 FF FFFF
		CROSSING_ERR_UNAUTHORIZED_IP(0x01000001,"Unauthorized client IP."),
		CROSSING_ERR_UNAUTHORIZED_INF(0x01000002,"Attempt to access unauthorized interface."),
		CROSSING_ERR_SERVER_NOT_FOUND(0x01000003,"Server not found."),
		CROSSING_ERR_CLASS_NOT_FOUND(0x01000004,"Class not found."),
		CROSSING_ERR_INTERFACE_NOT_FOUND(0x01000005,"Interface not found."),
		CROSSING_ERR_CLIENT_STARTUP(0x1000008,"Client startup failed."),
		
		CROSSING_ERR_INTERNAL(0x0100ffff,"Server internal error."),
		
		//Common ErrorCodes： 0xFF 00 0000 - 0xFF FF FFFE
		COMMON_ERR_PARAM_MALFORMED(0xff000011,"Parameter malformed."),
		COMMON_ERR_PARAM_MISSING(0xff000012,"Parameter missed."),
		
		COMMON_ERR_SIGN_BAD(0xff000021,"Bad signature."),
		COMMON_ERR_SIGN_MISSING(0xff000022,"Missing signature."),
		
		COMMON_ERR_ENCRYPTION(0x1000031,"Encryption error."),
		COMMON_ERR_DECRYPTION(0x1000032,"Decryption error."),
		
		//Always Unkown
		COMMON_ERR_UNKOWN(0xffffffff,"");
		
		private int code;
		private String info;
		
		private ErrorCode(int code, String info){
			this.code = code;
			this.info = info;
		}

		public int getCode() {
			return code;
		}

		public String getInfo() {
			return info;
		}
}
