package com.jdglazer.remote.dataflow.access;

import com.jdglazer.remote.dataflow.DataSource;

public class HTTPSAccess extends HTTPAccess {
	public HTTPSAccess() {
		setProtocol( DataSource.Protocol.https );
	}
}
