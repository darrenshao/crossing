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
package com.shc.crossing.startup;

/**
 * @author shc
 *
 */
public class CommandLine {
	private String[] commandline;
	private CommandLineParser parser;

	public CommandLine(String[] line){
		this.commandline = line;
		this.parser = new CommandLineParser();
	}
	
	public void printHelp(){
		StringBuilder help = new StringBuilder();
		help.append("Usage: \n");
		help.append("    -h Print this help message.\n");
		help.append("    -b binding address.\n");
		help.append("    -p listening port.\n");
		System.out.print(help.toString());
	}
	
	public void parse(){
		//
	}
}
