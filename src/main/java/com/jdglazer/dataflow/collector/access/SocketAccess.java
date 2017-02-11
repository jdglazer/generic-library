package com.jdglazer.dataflow.collector.access;

import java.io.Serializable;

import com.jdglazer.dataflow.collector.DataSource;

public class SocketAccess extends AccessCredentials implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	@Override
	public boolean equals( Object accessCred ) {
		if( !super.equals(accessCred) ) return false;
		if( !( accessCred instanceof  SocketAccess ) ) return false;
		SocketAccess saccess = (SocketAccess) accessCred;
		return saccess.getPort() == this.port && saccess.getIp().equals( this.ip );
	}
}
