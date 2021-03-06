/**
 * A class to hold access credentials needed to use datasource
 */
package com.jdglazer.dataflow.collector.access;

import java.io.Serializable;

import com.jdglazer.dataflow.collector.DataSource;

public class AccessCredentials implements Serializable {
	
	private DataSource.Protocol protocol;
	
	public void setProtocol( DataSource.Protocol protocol ) {
		this.protocol = protocol;
	}
	
	public DataSource.Protocol getProtocol() {
		return protocol;
	}
	
	@Override
	public boolean equals( Object access ) {
		if ( access == null ) return false;
		if ( !( access instanceof AccessCredentials ) ) return false;
		return this.protocol.equals( ( ( AccessCredentials ) access ).getProtocol() );
	}

}
