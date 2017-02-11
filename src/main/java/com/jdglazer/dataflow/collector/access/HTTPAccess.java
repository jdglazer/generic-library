package com.jdglazer.dataflow.collector.access;

import java.io.Serializable;
import java.util.Map;

import com.jdglazer.dataflow.collector.DataSource;

public class HTTPAccess extends AccessCredentials implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address;
	private Map<String, String> postVars;
	private Map<String, String> getVars;
	
	public HTTPAccess() {
		setProtocol( DataSource.Protocol.http );
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Map<String, String> getPostVars() {
		return postVars;
	}

	public void setPostVars(Map<String, String> postVars) {
		this.postVars = postVars;
	}

	public Map<String, String> getGetVars() {
		return getVars;
	}

	public void setGetVars(Map<String, String> getVars) {
		this.getVars = getVars;
	}

}
