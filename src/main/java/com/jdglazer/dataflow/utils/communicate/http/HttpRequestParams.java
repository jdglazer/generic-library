package com.jdglazer.dataflow.utils.communicate.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class HttpRequestParams {
	
	private String requestURL;
	
	private RequestType requestType = RequestType.GET;
	
	private long startReadOffset = 0;
	
	private Map< String, String > postGetVars;
	
	public HttpRequestParams setRequestURL( String url ) {
		this.requestURL = url;
		return this;
	}
	
	public HttpRequestParams setRequestType( RequestType type ) {
		this.requestType = type;
		return this;
	}
	
	public HttpRequestParams setStartReadOffset( long readOffset ) {
		startReadOffset = readOffset;
		return this;
	}
	
	public URL getRequestURL() throws MalformedURLException {
		return new URL ( requestType.equals(RequestType.GET ) 
							? HttpRequestParams.formatHttpURL( requestURL, postGetVars ) 
							: requestURL );
	}
	
	public RequestType getRequestType() {
		return requestType;
	}
	
	public long getStartReadOffset() {
		return startReadOffset;
	}
	
	public enum RequestType {
		GET, POST, PUT
	}

	public Map< String, String > getPostGetVars() {
		return postGetVars;
	}

	public HttpRequestParams setPostGetVars(Map< String, String > postGetVars) {
		this.postGetVars = postGetVars;
		return this;
	}
	
	/**
	 * Adds get variables on the end of an http/https url
	 * @param baseUrl The url without any get variables on the end of it
	 * @param getvars A map containing get variable names associated with their values
	 * @return Returns the base url with the get variable appended to the end
	 */
	public static String formatHttpURL( String baseUrl, Map< String, String > getvars ) {
		String getVars = "";
		if( getvars == null ) return baseUrl;
		if( getvars.size() > 0 ) {
			getVars="?";
			Set< Entry< String, String > > set = getvars.entrySet();
			for( Entry< String, String > entry : set ) {
				getVars+= entry.getKey()+"="+entry.getValue()+"&";
			}
			
			getVars = getVars.substring(0, getVars.length() - 1 );
		}
		return baseUrl+getVars;	
	}
}
