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
package club.jmint.crossing.bservice.worker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.thrift.transport.TTransportException;

import club.jmint.crossing.bservice.provider.ProviderWizard;
import club.jmint.crossing.bservice.provider.ThriftRpcProvider;
import club.jmint.crossing.bservice.provider.TransportClient;
import club.jmint.crossing.log.MyLog;

public class ReconnectWorker extends Thread {
	private HashMap<String, TransportClient> tcMap = null;
	private int timelap = 5000;
			
	
	@Override
	public void run() {
		ThriftRpcProvider provider = (ThriftRpcProvider) ProviderWizard.getProvider("ThriftRpc");
		tcMap = provider.getTransportClients();
		if (tcMap==null){ //this may occur on some circumstances
			return;
		}
		
		while(true){
			
			try {
				sleep(timelap);
				MyLog.logger.debug("ReconnectWorker tick.");
			} catch (InterruptedException e1) {
				MyLog.printStackTrace(e1);
			}
			
			
			Iterator<Entry<String, TransportClient>> it = tcMap.entrySet().iterator();
			Entry<String, TransportClient> en = null;
			TransportClient tc = null;
			while (it.hasNext()){
				en = it.next();
				
				tc = en.getValue();
				if(!tc.isConnected || !tc.transport.isOpen()){
					//reconnect to Thrift server
					try {
						tc.transport.open();
						MyLog.logger.info(getName() + ": Succeed to re-create transport to server: " 
								+ tc.serverName + " by "+ tc.serverIp + ":" + tc.serverPort);
						tc.isConnected = true;
					} catch (TTransportException e) {
						MyLog.printStackTrace(e);
						MyLog.logger.error(getName() + ": Failed to re-create transport to server: " 
								+ tc.serverName + " by "+ tc.serverIp + ":" + tc.serverPort);
						tc.isConnected = false;
						tc.connectTryTimes++;
						//continue;
					}
				}
			}
			
			
		}
		
	}

}
