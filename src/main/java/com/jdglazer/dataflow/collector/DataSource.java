package com.jdglazer.dataflow.collector;

import java.io.Serializable;

import com.jdglazer.dataflow.collector.access.AccessCredentials;
import com.jdglazer.dataflow.collector.parsers.ParserModelBase;

public class DataSource implements Serializable {

	private static final long serialVersionUID = 1543980989675665607L;

	//in milliseconds
	private int updateInterval;
	
	private String name;
	
	private AccessCredentials access;
	
	private ParserModelBase datasourceParser;
	
	public int getUpdateInterval() {
		return updateInterval;
	}
	
	public String getName() {
		return name;
	}
	
	public AccessCredentials getAccess() {
		return access;
	}
	
	public ParserModelBase getDatasourceParser() {
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
	
	public void setDatasourceParser( ParserModelBase datasourceParser ) {
		this.datasourceParser = datasourceParser;
	}
	
	public enum Protocol implements Serializable {
		http, https, ssh, socket
	}
	
	@Override
	public boolean equals( Object datasource ) {
		if( datasource == null ) return false;
		if( !( datasource instanceof DataSource) ) return false;
		DataSource ds = (DataSource) datasource;
		return ds.getUpdateInterval() == this.updateInterval && ds.getDatasourceParser().equals( this.datasourceParser ) && ds.getName().equals(this.name) && ds.getAccess().equals( this.access );
	}
}
