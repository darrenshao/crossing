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
package club.jmint.crossing.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

import club.jmint.crossing.utils.CrossLog;

/**
 * @author shc
 *
 */
public class AclConfig extends Config {
	private HashMap<String, AclRule> ruleMap=null;
	private HashMap<String, GrantedIPair> gcipMap=null;
	private HashMap<String, ArrayList<String>> gifMap=null;
	private boolean isAclEnabled = false;

	public AclConfig(String filePath) {
		super("AclConfig", filePath);
		init();
	}
	
	public boolean isIpAccessible(String clientIp){
		//if ACL not enabled, means all rights are permitted.
		if (!isAclEnabled){
			return true;
		}
		
//		AclRule ar = ruleMap.get("ipaddress");
//		if (ar==null){	//this rule not found, means that it's not enabled, "all permission allowed."
//			return true;
//		} else {
//			return ar.isEnabled();
//		}
		
		if (!ruleMap.get("ipaddress").isEnabled()){
			return true;
		}
		
		GrantedIPair gip = gcipMap.get(clientIp);
		if (gip==null){
			return false;
		} 
		else {
			return gip.isEnabled();
		}
	}
	
	public boolean isInterfaceAccessible(String clientIp, String inf){
		//if ACL not enabled, means all rights are permitted.
		if (!isAclEnabled){
			return true;
		}
		if (!ruleMap.get("interface").isEnabled()){
			return true;
		}
		
		ArrayList<String> al = gifMap.get(clientIp);
		if (al==null){
			return false;  //not found
		}
		
		Iterator<String> it = al.iterator();
		String en;
		while(it.hasNext()){
			en = it.next();
			if (en.equals(inf)){
				return true;
			}
		}
		return false;
	}

	private void init(){
		if (ruleMap==null){
			ruleMap = new HashMap<String, AclRule>();
		}
		if (gcipMap==null){
			gcipMap = new HashMap<String, GrantedIPair>();
		}
		if (gifMap==null){
			gifMap = new HashMap<String, ArrayList<String>>();
		}
	}
	
	/* (non-Javadoc)
	 * @see club.jmint.crossing.config.Config#loadConfig()
	 */
	@Override
	public Config loadConfig() {
		super.loadConfig();
		XMLConfiguration conf = loadXMLConfigFile(configFilePath);

		
		ConfigurationNode root = conf.getRootNode();
		List<ConfigurationNode> aclenabled = root.getAttributes("enabled");
		isAclEnabled = Boolean.parseBoolean(aclenabled.get(0).getValue().toString());
		//System.out.println(aclenabled.get(0).getValue().toString());
		//System.out.println(isAclEnabled);
		
		List<ConfigurationNode> rulesnode = root.getChildren("rules");
		List<ConfigurationNode> rulenode = rulesnode.get(0).getChildren();
		ConfigurationNode node;
		List<ConfigurationNode> name,enabled;
		String rname;
		for(int i=0;i<rulenode.size();i++){
			node = rulenode.get(i);
			name = node.getAttributes("name");
			enabled = node.getAttributes("enabled");
			
			//System.out.println(name.get(0).getValue().toString());
			//System.out.println(enabled.get(0).getValue().toString());;
			rname = name.get(0).getValue().toString();
			ruleMap.put(rname, new AclRule(rname, Boolean.parseBoolean(enabled.get(0).getValue().toString())));
			
		}		

		
		List<ConfigurationNode> gcsnode = root.getChildren("grantedclient");
		List<ConfigurationNode> gcnode = gcsnode.get(0).getChildren();
		//ConfigurationNode node;
		List<ConfigurationNode> ip,desc;
		String iname;
		for(int i=0;i<gcnode.size();i++){
			node = gcnode.get(i);
			name = node.getAttributes("name");
			ip = node.getAttributes("ip");
			enabled = node.getAttributes("enabled");
			desc = node.getAttributes("desc");
			
			//System.out.println(name.get(0).getValue().toString());
			//System.out.println(ip.get(0).getValue().toString());
			//System.out.println(access.get(0).getValue().toString());
			iname = name.get(0).getValue().toString();
			gcipMap.put(iname, new GrantedIPair(iname,
					ip.get(0).getValue().toString(),
					Boolean.parseBoolean(enabled.get(0).getValue().toString()),
					desc.get(0).getValue().toString()));
			
		}	
		
		
		List<ConfigurationNode> gisnode = root.getChildren("grantedif");
		List<ConfigurationNode> ginode = gisnode.get(0).getChildren();
		ConfigurationNode clientnode;
		List<ConfigurationNode> client,value;
		ArrayList<String> al;
		for(int i=0;i<ginode.size();i++){
			node = ginode.get(i);
			name = node.getAttributes("name");
			//System.out.println(node.getName());
			//System.out.println(name.get(0).getValue().toString());
			
			al = new ArrayList<String>();
			client = node.getChildren();
			for(int j=0;j<client.size();j++){
				clientnode = client.get(j);
				value = clientnode.getAttributes("value");
				//System.out.println(value.get(0).getValue().toString());
				al.add(value.get(0).getValue().toString());
			}
			gifMap.put(name.get(0).getValue().toString(), al);
			//System.out.println(gifMap.get(name.get(0).getValue().toString()).get(0));
		
		}	

		return this;
	}

	/* (non-Javadoc)
	 * @see club.jmint.crossing.config.Config#print()
	 */
	@Override
	public void print() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(name+":\n");
		sb.append("Rules:\n");
		Iterator<Entry<String,AclRule>> it = ruleMap.entrySet().iterator();
		Entry<String,AclRule> en;
		while(it.hasNext()){
			en = it.next();
			sb.append(String.format("%-20s= %s\n", en.getKey(), en.getValue().isEnabled()));
		}
		
		sb.append("Granted clients:\n");
		Iterator<Entry<String, GrantedIPair>> gcipit = gcipMap.entrySet().iterator();
		Entry<String,GrantedIPair> gcipen;
		while(gcipit.hasNext()){
			gcipen = gcipit.next();
			sb.append(String.format("%-40s= %s\n", gcipen.getKey()
					+"("+gcipen.getValue().getIp()+")",gcipen.getValue().isEnabled()));
		}
		
		sb.append("Granted clients interfaces:\n");
		Iterator<Entry<String, ArrayList<String>>> gifit = gifMap.entrySet().iterator();
		Entry<String,ArrayList<String>> gifen;
		while(gifit.hasNext()){
			gifen = gifit.next();
			sb.append(gifen.getKey()+":\n");
			ArrayList<String> ifs = gifen.getValue();
			for(int i=0;i<ifs.size();i++){
				sb.append(ifs.get(i)+"\n");
			}
		}	
		CrossLog.logger.info(sb.toString());
	}
}
