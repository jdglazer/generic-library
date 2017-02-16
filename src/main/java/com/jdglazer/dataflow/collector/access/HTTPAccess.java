package com.jdglazer.dataflow.collector.access;

import java.io.Serializable;
import java.util.Map;

import com.jdglazer.dataflow.collector.DataSource;
import com.jdglazer.dataflow.collector.crawlers.Crawler;

public class HTTPAccess extends AccessCredentials implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address;
	private Crawler crawler;
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
	
	public Crawler getCrawler() {
		return crawler;
	}

	public void setCrawler(Crawler crawler) {
		this.crawler = crawler;
	}
	
	@Override
	public boolean equals( Object accessCred ) {
		if( !super.equals( accessCred ) ) return false;
		if( !( accessCred instanceof HTTPAccess ) ) return false;
		HTTPAccess haccess = (HTTPAccess) accessCred;
		return haccess.getAddress() == this.address && haccess.getGetVars().equals( this.getVars ) && haccess.getPostVars().equals( this.postVars );
	}

}
