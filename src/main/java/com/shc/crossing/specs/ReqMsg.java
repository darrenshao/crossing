package com.shc.crossing.specs;

public class ReqMsg {
	public String ip;
	public long seqId;
	public String server;
	public String inf;
	public String params;
	public boolean isEncrypt;
	
	public ReqMsg(){}
	
	public ReqMsg(String ip, long seqId, String server, String inf, String params, boolean isEncrypt) {
		super();
		this.ip = ip;
		this.seqId = seqId;
		this.server = server;
		this.inf = inf;
		this.params = params;
		this.isEncrypt = isEncrypt;
	}
	
	
}
