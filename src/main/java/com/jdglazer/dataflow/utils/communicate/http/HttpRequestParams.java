package com.jdglazer.dataflow.utils.communicate.http;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestParams {
	
	private URL requestURL;
	
	private String requestType;
	
	private long startReadOffset = 0;
	
	public HttpRequestParams setRequestURL( String url ) throws MalformedURLException {
		this.requestURL = new URL(url);
		return this;
	}
	
	public HttpRequestParams setRequestType( String type ) {
		this.requestType = type;
		return this;
	}
	
	public HttpRequestParams setStartReadOffset( long readOffset ) {
		startReadOffset = readOffset;
		return this;
	}
	
	public URL getRequestURL() {
		return requestURL;
	}
	
	public String getRequestType() {
		return requestType;
	}
	
	public long getStartReadOffset() {
		return startReadOffset;
	}

}
