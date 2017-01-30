package com.jdglazer.communicate;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdglazer.utils.communicate.HttpRequest;

public class HttpRequestManager implements Runnable {
	
	Logger logger = LoggerFactory.getLogger(HttpRequestManager.class);
	
	private HashMap<String, HttpRequest> requestMap = new HashMap<String, HttpRequest>();
	private HashMap<String, Thread> threadMap = new HashMap<String, Thread>();
	
	public HttpRequestManager(){
	}
	

	public void run() {
		
	}
	
	public boolean addHttpRequest( String name, HttpRequest request ) {
		boolean newRequest = requestMap.get(name) == null;
		requestMap.put( name, request);
		return newRequest;
	}
	
	public boolean removeHttpRequest( String name, boolean waitForThread ) {
		Thread thread = threadMap.get(name);
		return requestMap.remove(name) != null;
	}
	
	public int getRequestCount() {
		return requestMap.size();
	}
	
	public long getAllRequestsSize() {
		long requestSize = 0;
		for( String req : requestMap.keySet() )
			requestSize += requestMap.get(req).getHttpInputSize();
		return requestSize;
	}
	
	public long getAllRequestProgress() {
		long requestProgress = 0;
		for( String req : requestMap.keySet() )
			requestProgress += requestMap.get( req ).getHttpInputProgress();
		return requestProgress;
	}
	
	public boolean hasRunningThread( String name ) {
		Thread thread = threadMap.get(name);
		return thread == null ? false : thread.isAlive();
	}
}
