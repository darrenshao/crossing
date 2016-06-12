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

import club.jmint.crossing.log.MyLog;
import club.jmint.crossing.utils.Utils;

/**
 * @author shc
 *
 */
public class StatSnapshotThread extends Thread{
	private long timegap;

	public StatSnapshotThread(long gap){
		super();
		this.timegap = gap;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
		
		String ss;
		while(true){
			
			try {
				sleep(timegap);
				ss = StatsWizard.createStatsSnapshot();
				MyLog.logger.info("Statistics snapshot("+Utils.getDateTime()+"): \n" + ss);
			} catch (InterruptedException e) {
				MyLog.printStackTrace(e);
			}
			
		}
	}

}
