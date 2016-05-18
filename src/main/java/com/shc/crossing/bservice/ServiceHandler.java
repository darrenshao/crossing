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
package com.shc.crossing.bservice;

import com.shc.crossing.bservice.provider.Provider;
import com.shc.crossing.bservice.provider.ProviderWizard;
import com.shc.crossing.config.ConfigWizard;
import com.shc.crossing.config.CrossingConfig;
import com.shc.crossing.config.ProviderPair;
import com.shc.crossing.exception.CrossException;
import com.shc.crossing.log.MyLog;
import com.shc.crossing.runtime.Constants;
import com.shc.crossing.specs.ReqMsg;

/**
 * @author shc
 *
 */
public class ServiceHandler implements IHandler{

	public String handle(ReqMsg rmsg) throws CrossException {
				
		CrossingConfig cc = (CrossingConfig)ConfigWizard.getConfig(Constants.CONFIG_CROSSING);
		String ptype = cc.getServerType(rmsg.server);
		ProviderPair provider = cc.getProvider(ptype);
		//String infclass = provider.getClazz();
		
		int pos = rmsg.inf.indexOf("@");
		String classname = rmsg.inf.substring(0, pos);
		String infname = rmsg.inf.substring(pos+1);
		
		String ret = null;
		Provider pro = ProviderWizard.getProvider(provider.getName());
		ret = pro.execute(rmsg.server, classname, infname, rmsg.params, rmsg.isEncrypt);
		MyLog.logger.info("Call executed: " + rmsg.server + " ==> " + classname + " " + infname);
		
		return ret;
	}

}
