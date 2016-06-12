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
package club.jmint.crossing.stats;

import java.util.HashMap;

import club.jmint.crossing.log.MyLog;

/**
 * @author shc
 *
 */
public class ServerCallStats extends CallStats {
	private String inf;
	
	/**
	 * record interface call times for echo client.
	 */
	private HashMap<String,CounterPair> infs = new HashMap<String, CounterPair>();

	public ServerCallStats(String name, String inf) {
		super(name);
		this.inf = inf;
	}
	
	private CounterPair addCounterPair(String item, int counter){
		CounterPair	cp = new CounterPair(item,counter);
		infs.put(item, cp);
		MyLog.logger.info("CounterPair: item="+item+" created.");
		return cp;
	}

	public CounterPair getCounterPair(String item){
		return infs.get(item);
	}
	
	public void removeCounterPair(String item){
		infs.remove(item);
	}
	
	public void setCounterPair(String item, int counter){
		CounterPair cp = infs.get(item);
		if (cp==null){ 
			MyLog.logger.warn("CounterPair: item="+item+" not found.");
			addCounterPair(item, 0);
			return ;
		}
		cp.setCounter(counter);
	}
	
	public void incCounterPair(String item){
		CounterPair cp = infs.get(item);
		if (cp==null){ 
			MyLog.logger.warn("CounterPair: item="+item+" not found.");
			addCounterPair(item, 0);
			return ;
		}
		cp.setCounter(cp.getCounter()+1);	
	}
	
}
