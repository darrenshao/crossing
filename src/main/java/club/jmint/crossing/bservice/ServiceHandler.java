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
package club.jmint.crossing.bservice;

import club.jmint.crossing.bservice.provider.Provider;
import club.jmint.crossing.bservice.provider.ProviderWizard;
import club.jmint.crossing.config.ConfigWizard;
import club.jmint.crossing.config.CrossingConfig;
import club.jmint.crossing.config.ProviderPair;
import club.jmint.crossing.exception.CrossException;
import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.runtime.Constants;
import club.jmint.crossing.specs.ReqMsg;

/**
 * @author shc
 *
 */
public class ServiceHandler implements IHandler{

	public String handle(ReqMsg rmsg) throws CrossException {
		//parse rmsg.inf with format: xxx@yyy@zzz
		int pos1 = rmsg.inf.indexOf("@");
		String servername = rmsg.inf.substring(0, pos1);
		String remainder = rmsg.inf.substring(pos1+1);
		
		int pos2 = remainder.indexOf("@");
		String classname = remainder.substring(0, pos2);
		
		String infname = remainder.substring(pos2+1);
				
		CrossingConfig cc = (CrossingConfig)ConfigWizard.getConfig(Constants.CONFIG_CROSSING);
		String ptype = cc.getServerType(servername);
		ProviderPair provider = cc.getProvider(ptype);
		//String infclass = provider.getClazz();
		
		String ret = null;
		Provider pro = ProviderWizard.getProvider(provider.getName());
		ret = pro.execute(servername, classname, infname, rmsg.params, rmsg.isEncrypt);
		MyLog.logger.info("Call executed: " + servername + " ==> " + classname + " ==> " + infname);
		
		return ret;
	}

}
