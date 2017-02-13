package com.jdglazer.dataflow.utils.communicate.https;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.net.ssl.HttpsURLConnection;

import com.jdglazer.dataflow.utils.communicate.http.HttpRequest;

public class HttpsRequest extends HttpRequest {

	public HttpsRequest( HttpsRequestParams params ) {
		super( params );
		try {
			connect();
			
		} catch (MalformedURLException e) {	} catch (IOException e) {	}
	}
	
	private boolean connect() throws MalformedURLException, IOException {
		setConnection( ( HttpsURLConnection ) getParams().getRequestURL().openConnection() );
		return buildConnection();
	}

}
