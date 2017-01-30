package com.jdglazer.remote.dataflow;

public class DataSource {
	//in milliseconds
	private int updateInterval;
	
	private String name;
	
	private Protocol protocol;
	
	private AccessCredentials access;
	
	private DatasourceParserBase datasourceParser;
	
	public int getUpdateInterval() {
		return updateInterval;
	}
	
	public String getName() {
		return name;
	}
	
	public AccessCredentials getAccess() {
		return access;
	}
	
	public DatasourceParserBase getDatasourceParser() {
		return datasourceParser;
	}
	
	public Protocol getProtocol() {
		return protocol;
	}
	
	public void setUpdateInterval( int updateInterval) {
		this.updateInterval = updateInterval;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public void setAccess( AccessCredentials access ) {
		this.access = access;
	}
	
	public void getDatasourceParser( DatasourceParserBase datasourceParser ) {
		this.datasourceParser = datasourceParser;
	}
	
	public void getProtocol( Protocol protocol ) {
		this.protocol = protocol;
	}
	
	public enum Protocol {
		http, https, ssh, socket
	}
}
