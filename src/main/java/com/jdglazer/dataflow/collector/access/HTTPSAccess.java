package com.jdglazer.remote.dataflow.access;

import java.io.Serializable;

import com.jdglazer.remote.dataflow.DataSource;

public class HTTPSAccess extends HTTPAccess implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HTTPSAccess() {
		setProtocol( DataSource.Protocol.https );
	}
}
