package com.jdglazer.dataflow.collector.access;

import java.io.Serializable;

import com.jdglazer.dataflow.collector.DataSource;

public class HTTPSAccess extends HTTPAccess implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HTTPSAccess() {
		setProtocol( DataSource.Protocol.https );
	}
	
	@Override
	public boolean equals( Object accessCreds ) {
		return super.equals(accessCreds);
	}
}
