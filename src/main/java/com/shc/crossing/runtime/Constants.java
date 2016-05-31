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
public class Constants {
	/*
	 * Define some constants used at runtime.
	 */
	public final static String DIR_HOME_PARENT_DEFAULT = "/data/";
	
	public final static String DIR_BIN = "bin";
	public final static String DIR_CONF = "conf";
	public final static String DIR_LIB = "lib";
	public final static String DIR_DOCS = "docs";
	public final static String DIR_LOG = "log";
	
	public final static String PROJECT_NAME = "crossing";
	
	public final static String CONFIG_ACL = "config.acl";
	public final static String CONFIG_SERVER = "config.server";
	public final static String CONFIG_CROSSING = "config.crossing";
	
	public final static String SERVER_TYPE_LOCALRPC = "LocalCall";
	public final static String SERVER_TYPE_SHARPRPC = "SharpRpc";
	public final static String SERVER_TYPE_THRIFTRPC = "ThriftRpc";
	
	public final static int SERVER_PORT = 9000;
	
	public final static int SERVICE_CALL_TYPE_SHARPRPC = 0x01;
	public final static int SERVICE_CALL_TYPE_THRIFTRPC = 0x02;
	
}
