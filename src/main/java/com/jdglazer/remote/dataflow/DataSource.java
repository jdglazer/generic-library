package com.jdglazer.remote.dataflow;

import java.io.Serializable;

import com.jdglazer.remote.dataflow.access.AccessCredentials;

public class DataSource implements Serializable {

	private static final long serialVersionUID = 1543980989675665607L;

	//in milliseconds
	private int updateInterval;
	
	private String name;
	
	private AccessCredentials access;
	
	private DataSourceParserBase datasourceParser;
	
	public int getUpdateInterval() {
		return updateInterval;
	}
	
	public String getName() {
		return name;
	}
	
	public AccessCredentials getAccess() {
		return access;
	}
	
	public DataSourceParserBase getDatasourceParser() {
		return datasourceParser;
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
	
	public void getDatasourceParser( DataSourceParserBase datasourceParser ) {
		this.datasourceParser = datasourceParser;
	}
	
	public enum Protocol {
		http, https, ssh, socket
	}
}
