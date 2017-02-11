package com.jdglazer.dataflow.collector.access;

import java.io.Serializable;

import com.jdglazer.dataflow.collector.DataSource;

public class SSHAccess extends AccessCredentials implements Serializable {
	
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
	
	@Override
	public boolean equals( Object sshAccess ) {
		if( !super.equals( sshAccess ) ) return false;
		if( !( sshAccess instanceof SSHAccess ) ) return false;
		SSHAccess saccess = ( SSHAccess ) sshAccess;
		return saccess.getIp().equals( this.ip ) && saccess.getPassword().equals( this.password ) && saccess.getUser().equals( this.user ); 
	}
}
