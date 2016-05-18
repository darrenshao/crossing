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
package com.shc.crossing.stats;

import java.util.*;
import java.util.Map.Entry;

import com.shc.crossing.wizard.Wizard;

/**
 * @author shc
 *
 */
public class StatsWizard extends Wizard {
	private long defaultTimeGap = 120000;
	private StatSnapshotThread sst;
	
	public StatsWizard(String name) {
		super(name);
	}


	private static HashMap<String, ClientCallStats> clientcallstatsMap = null;
	private static HashMap<String, ServerCallStats> servercallstatsMap = null;
	
	/* (non-Javadoc)
	 * @see io.wizard.Wizard#init()
	 */
	@Override
	public void init() {
		super.init();
		
		if (clientcallstatsMap==null){
			clientcallstatsMap = new HashMap<String, ClientCallStats>();
		}
		if (servercallstatsMap==null){
			servercallstatsMap = new HashMap<String, ServerCallStats>();
		}
		sst = new StatSnapshotThread(this.defaultTimeGap);
		sst.start();
	}

	/* (non-Javadoc)
	 * @see io.wizard.Wizard#startup()
	 */
	@Override
	public void startup() {
		// TODO Auto-generated method stub
		super.startup();
	}

	/* (non-Javadoc)
	 * @see io.wizard.Wizard#shutdown()
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		super.shutdown();
	}

	public static ClientCallStats getClientCallStats(String clientIp){
		if (clientIp==null) return null;
		ClientCallStats statsObj = clientcallstatsMap.get(clientIp);
		if (statsObj==null){
			statsObj = new ClientCallStats("",clientIp);
			clientcallstatsMap.put(clientIp, statsObj);
		}
		return statsObj;
	}
	
	public static ServerCallStats getServerCallStats(String inf){
		if (inf==null) return null;
		ServerCallStats statsObj = servercallstatsMap.get(inf);
		if (statsObj==null){
			statsObj = new ServerCallStats("",inf);
			servercallstatsMap.put(inf, statsObj);
		}
		return statsObj;
	}
	
	
	/**
	 * take a snapshot view of the full statistics.
	 * @return a string represent snapshot of the statistics.
	 */
	public static String createStatsSnapshot(){
		StringBuffer snapshot = new StringBuffer();
		
		//construct the table header
		String frm = "%-30s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n";
		//snapshot.append("\n");
		snapshot.append(String.format(frm, 
				"Client IP","times","timeouts","success","failures","Max Delays","Min Delays","Ave. Delays"));
		
		Iterator<Entry<String, ClientCallStats>> it = clientcallstatsMap.entrySet().iterator();
		Entry<String, ClientCallStats> entry = null;
		String key = null;
		ClientCallStats val = null;
		CounterPair cp;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			entry = it.next();
			key = entry.getKey();
			val = entry.getValue();
			snapshot.append(String.format(frm, 
				key,val.getTimes(),val.getTimeouts(),val.getSuccesses(),
				val.getFailures(),val.getMaxDelays(),val.getMinDelays(),val.getTotalDelays()/val.getTimes()));
			//System.out.println(key + val);
			
//			sb.append("Client IP(" + key + ") ==> interface list: \n");
//			sb.append(String.format("%-40s %-40s", "Interface", "Counter"));
//			while(val.hasNext()){
//				cp = val.next();
//				sb.append(String.format("%-40s %-40s", cp.getItem(), cp.getCounter()));
//			}
		}
		snapshot.append("\n");
		
		//Client => server interface call times
		snapshot.append(sb.toString());
		snapshot.append("\n");
		
		//construct the table header
//		snapshot.append(String.format(frm, 
//				"Interface(server)","times","timeouts","success","failures","Max Delays","Min Delays","Ave. Delays"));
//		Iterator<Entry<String, ServerCallStats>> it2 = servercallstatsMap.entrySet().iterator();
//		Entry<String, ServerCallStats> entry2 = null;
//		String key2 = null;
//		ServerCallStats val2 = null;
//		while (it2.hasNext()) {
//			entry2 = it2.next();
//			key2 = entry2.getKey();
//			val2 = entry2.getValue();
//			snapshot.append(String.format(frm, 
//				key2,val2.getTimes(),val2.getTimeouts(),val2.getSuccesses(),
//				val2.getFailures(),val2.getMaxDelays(),val2.getMinDelays(),val.getTotalDelays()/val.getTimes()));
//			//System.out.println(key2 + val2);
//		}
		snapshot.append("\n\n");
		
		return snapshot.toString();
	}
	
	
}
