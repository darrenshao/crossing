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
public class CrossingConfig extends Config {
	private HashMap<String,ProviderPair> ptypesMap;
	private HashMap<String,ServerPair> serversMap;
	private HashMap<String,ArrayList<InfPair>> infsMap;
	
	public CrossingConfig(String filePath) {
		super("CrossingConfig", filePath);
		init();
	}
	
	public String getServerType(String server){
		ServerPair sp = serversMap.get(server);
		if (sp!=null){
			return sp.getType();
		}
		return null;
	}
	
	public ProviderPair getProvider(String type){
		return ptypesMap.get(type);
	}
	
	public ArrayList<ServerPair> getServers(String type){
		ArrayList<ServerPair> al = new ArrayList<ServerPair>();
		Iterator<Entry<String,ServerPair>> it = serversMap.entrySet().iterator();
		Entry<String,ServerPair> en;
		while(it.hasNext()){
			en = it.next();
			if ((type!=null) && type.equals(en.getValue().getType())){
				al.add(en.getValue());
			}
		}
		return al;		
	}
	
	public ArrayList<ProviderPair> getProviders(){
		ArrayList<ProviderPair> al = new ArrayList<ProviderPair>();
		Iterator<Entry<String,ProviderPair>> it = ptypesMap.entrySet().iterator();
		Entry<String,ProviderPair> en;
		while(it.hasNext()){
			en = it.next();
			al.add(en.getValue());
		}
		return al;
	}
	
	public ArrayList<InfPair> getInterfaceList(String server){
		return infsMap.get(server);
	}
	
	private void init(){
		if (ptypesMap==null){
			ptypesMap = new HashMap<String, ProviderPair>();
		}
		if (serversMap==null){
			serversMap = new HashMap<String, ServerPair>();
		}
		if (infsMap==null){
			infsMap = new HashMap<String, ArrayList<InfPair>>();
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

		List<ConfigurationNode> ptypesnode = root.getChildren("providertypes");
		List<ConfigurationNode> typenode = ptypesnode.get(0).getChildren();
		ConfigurationNode node;
		List<ConfigurationNode> name,enabled,clazz;
		for(int i=0;i<typenode.size();i++){
			node = typenode.get(i);
			name = node.getAttributes("name");
			enabled = node.getAttributes("enabled");
			clazz = node.getAttributes("class");
			//System.out.println(name.get(0).getValue());
			//System.out.println(enabled.get(0).getValue());
			
			ptypesMap.put(name.get(0).getValue().toString(), new ProviderPair(
					name.get(0).getValue().toString(),
					Boolean.parseBoolean(enabled.get(0).getValue().toString()),
					clazz.get(0).getValue().toString()));
		}		

		
		List<ConfigurationNode> srvsnode = root.getChildren("servers");
		List<ConfigurationNode> srvnode = srvsnode.get(0).getChildren();
		//ConfigurationNode node;
		List<ConfigurationNode> type, iplist, port, mp;
		for(int i=0;i<srvnode.size();i++){
			node = srvnode.get(i);
			name = node.getAttributes("name");
			type = node.getAttributes("type");
			mp = node.getAttributes("methodProxy");
			iplist = node.getAttributes("ip");
			port = node.getAttributes("port");

			serversMap.put(name.get(0).getValue().toString(), new ServerPair(
					name.get(0).getValue().toString(),
					type.get(0).getValue().toString(),
					mp.get(0).getValue().toString(),
					iplist.get(0).getValue().toString(),
					Integer.parseInt(port.get(0).getValue().toString())
					));
		}	
		
		
		List<ConfigurationNode> infsnode = root.getChildren("interfaces");
		List<ConfigurationNode> infnode = infsnode.get(0).getChildren();
		ConfigurationNode servernode;
		List<ConfigurationNode> sname,intf,iname,encrypt;
		ArrayList<InfPair> al;
		for(int i=0;i<infnode.size();i++){
			node = infnode.get(i);
			sname = node.getAttributes("name");
			
			al = new ArrayList<InfPair>();
			intf = node.getChildren();
			for(int j=0;j<intf.size();j++){
				servernode = intf.get(j);
				iname = servernode.getAttributes("name");
				encrypt = servernode.getAttributes("isEncrypt");
				
				al.add(new InfPair(iname.get(0).getValue().toString(),
						Boolean.parseBoolean(encrypt.get(0).getValue().toString())));
			}
			infsMap.put(sname.get(0).getValue().toString(), al);		
		}
		return this;
	}




	/* (non-Javadoc)
	 * @see club.jmint.crossing.config.Config#print()
	 */
	@Override
	public void print() {
		StringBuffer sb = new StringBuffer();
		sb.append(name+":\n");
		sb.append("Provider Types:\n");
		Iterator<Entry<String,ProviderPair>> it = ptypesMap.entrySet().iterator();
		Entry<String,ProviderPair> en;
		while(it.hasNext()){
			en = it.next();
			sb.append(String.format("%-20s= %s\n", en.getKey(), en.getValue()));
		}
		
		sb.append("Servers:\n");
		Iterator<Entry<String, ServerPair>> sit = serversMap.entrySet().iterator();
		Entry<String,ServerPair> sen;
		while(sit.hasNext()){
			sen = sit.next();
			sb.append(String.format("%-20s(%-16s/%d) = %s(%s)\n", sen.getKey(), 
					sen.getValue().getIplist(), sen.getValue().getPort(), 
					sen.getValue().getType(), sen.getValue().getMethodProxy()));
		}
		
		sb.append("Interfaces:\n");
		Iterator<Entry<String, ArrayList<InfPair>>> fit = infsMap.entrySet().iterator();
		Entry<String,ArrayList<InfPair>> fen;
		while(fit.hasNext()){
			fen = fit.next();
			sb.append(fen.getKey()+":\n");
			ArrayList<InfPair> ifs = fen.getValue();
			for(int i=0;i<ifs.size();i++){
				sb.append(String.format("%-40s %-10s\n",ifs.get(i).getInfName(),ifs.get(i).getIsEncrypt()));
			}
		}	
		CrossLog.logger.info(sb.toString());
	}
}
