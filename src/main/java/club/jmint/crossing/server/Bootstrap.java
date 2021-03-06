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
package club.jmint.crossing.server;

import club.jmint.crossing.wizard.WizardManager;

/**
 * @author shc
 *
 */
public class Bootstrap {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Get command line parameters and parse
		CommandLine cl = new CommandLine(args);
		cl.parse();
		
		//Do initialization
		WizardManager.initWizard();
		
		//Startup all components
		WizardManager.startupWizard();
		
		//Server statup
		CrossingServer cs = new CrossingServer();
		cs.start();
		
		//Shutdown all components
		WizardManager.shutdownWizard();
	}

}
