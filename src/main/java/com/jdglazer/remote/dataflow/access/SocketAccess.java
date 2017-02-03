package com.jdglazer.remote.dataflow.access;

import com.jdglazer.remote.dataflow.DataSource;

public class SocketAccess extends AccessCredentials {
	
	private String ip;
	private short port;
	
	public SocketAccess() {
		setProtocol( DataSource.Protocol.socket );
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public short getPort() {
		return port;
	}
	public void setPort(short port) {
		this.port = port;
	}
}
