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

import java.util.Calendar;

/**
 * @author shc
 *	Call Statistics
 */
public class CallStats implements IStats{
	private String name = "";
	
	private long times = 0;
	private long timeouts = 0;
	private long successes = 0; 
	private long failures = 0;
	private long maxDelays = 0;
	private long minDelays = Long.MAX_VALUE;
	private long totalDelays = 0;	//for calculating the average delay
	
	//Statistics calculating startup timestamp
	private long timestamp_start;
	
	public CallStats(String name){
		this.name = name;
		timestamp_start = Calendar.getInstance().getTimeInMillis();
	}

	public synchronized String getName() {
		return name;
	}

	private synchronized void setName(String name) {
		this.name = name;
	}

	public synchronized long getTimes() {
		return times;
	}

	public synchronized void setTimes(long times) {
		this.times = times;
	}

	public synchronized long getTimeouts() {
		return timeouts;
	}

	public synchronized void setTimeouts(long timeouts) {
		this.timeouts = timeouts;
	}

	public synchronized long getSuccesses() {
		return successes;
	}

	public synchronized void setSuccesses(long successes) {
		this.successes = successes;
	}

	public synchronized long getFailures() {
		return failures;
	}

	public synchronized void setFailures(long failures) {
		this.failures = failures;
	}

	public synchronized long getMaxDelays() {
		return maxDelays;
	}

	public synchronized void setMaxDelays(long delay) {
		if (maxDelays < delay){
			this.maxDelays = delay;			
		}
	}

	public synchronized long getMinDelays() {
		return minDelays;
	}

	public synchronized void setMinDelays(long delay) {
		if (minDelays > delay){
			this.minDelays = delay;
		}
	}
	
	public synchronized long getTotalDelays(){
		return totalDelays;
	}
	
	public synchronized void addToTotalDelay(long delay){
		this.totalDelays = this.totalDelays + delay;
	}
	
	public synchronized void inc(String name) {
		// TODO Auto-generated method stub
		if (name == null) return;
		if (name.equals("times")){
			this.times++;
		}
		else if (name.equals("timeouts")){
			this.timeouts++;
		}
		else if (name.equals("successes")){
			this.successes++;
		}
		else if (name.equals("failures")){
			this.failures++;
		}
		else if (name.equals("totalDelays")){
			this.totalDelays++;
		}
		else{
			return;
		}
	}

}
