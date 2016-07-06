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

import club.jmint.crossing.utils.CrossLog;
import club.jmint.crossing.wizard.Wizard;

public class WorkerWizard extends Wizard {
	private HashMap<String, Thread> workers = new HashMap<String, Thread>();

	public WorkerWizard(String name) {
		super(name);
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public void init() {
		super.init();
		if (workers==null){
			workers = new HashMap<String, Thread>();
		}
		workers.put("ReconnectWorker", new ReconnectWorker());
	}

	@Override
	public void startup() {
		super.startup();
		Iterator<Entry<String, Thread>> it = workers.entrySet().iterator();
		Entry<String, Thread> en;
		Thread t;
		while(it.hasNext()){
			en = it.next();
			t = en.getValue();
			t.start();
			CrossLog.logger.info(en.getKey() + "("+ t.getId() +") has started.");
		}
		
	}

	@Override
	public void shutdown() {
		super.shutdown();
	}

	
}
