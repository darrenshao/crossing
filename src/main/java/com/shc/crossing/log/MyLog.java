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
package com.shc.crossing.log;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shc.crossing.runtime.Constants;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;



/**
 * @author shc
 *
 */
public class MyLog {
	static {
		PropertyConfigurator.configure(Constants.DIR_CONF + File.separator + "log4j.properties"); 
	}
	
	public final static Logger logger = LoggerFactory.getLogger(MyLog.class);
	
	public final static StringWriter sw = new StringWriter();
	public final static  PrintWriter pw = new PrintWriter(sw);

	public static void printStackTrace(Exception e){
        e.printStackTrace(pw);
        logger.error(sw.toString());
	}
}