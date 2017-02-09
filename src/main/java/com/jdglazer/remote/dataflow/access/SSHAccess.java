package com.jdglazer.remote.dataflow.access;

import java.io.Serializable;

import com.jdglazer.remote.dataflow.DataSource;

public class SSHAccess extends AccessCredentials implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String user;
	private String ip;
	private String password;
	
	public SSHAccess() {
		setProtocol( DataSource.Protocol.ssh );
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
