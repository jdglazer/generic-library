/**
 * A class to hold access credentials needed to use datasource
 */
package com.jdglazer.remote.dataflow;

public class AccessCredentials {
	
	/**
	 * The http/https address
	 */
	private String address;
	/**
	 * An ssh user
	 */
	private String user;
	/**
	 * The ssh password
	 */
	private String password;
	/**
	 * ssh or socket ip
	 */
	private String ip;
	/**
	 * The port for socket connections
	 */
	private short port;
	/**
	 * The key for socket connections
	 */
	private String key;
	
	public void setAddress( String address ) {
		this.address = address;
	}
	
	public void setUser ( String user ) {
		this.user = user;
	}
	
	public void setPassword( String password ) {
		this.password = password;
	}
	
	public void setIp ( String ip ) {
		this.ip = ip;
	}
	
	public void setPort ( short port ) {
		this.port = port;
	}
	
	public void setKey ( String key ) {
		this.key = key;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getIp () {
		return ip;
	}
	
	public short getPort () {
		return port;
	}
	
	public String setKey() {
		return key;
	}

}
