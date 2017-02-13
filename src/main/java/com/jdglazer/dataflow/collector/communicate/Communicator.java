/**
 * A class used to open a line of communication based on a given set of access credentials
 * AccessCredentials -> Communicator -> com.jdglazer.dataflow.utils.communicate.*
 * @author Glazer, Joshua D.
 */
package com.jdglazer.dataflow.collector.communicate;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdglazer.dataflow.collector.access.AccessCredentials;
import com.jdglazer.dataflow.collector.access.HTTPAccess;
import com.jdglazer.dataflow.utils.communicate.Request;
import com.jdglazer.dataflow.utils.communicate.http.HttpRequest;
import com.jdglazer.dataflow.utils.communicate.http.HttpRequestParams;
import com.jdglazer.dataflow.utils.communicate.http.HttpRequestParams.RequestType;
import com.jdglazer.dataflow.utils.communicate.https.HttpsRequest;
import com.jdglazer.dataflow.utils.communicate.https.HttpsRequestParams;

public class Communicator {
	
	Logger logger = LoggerFactory.getLogger( Communicator.class );
	
	private AccessCredentials accessCredentials;
	
	public Communicator( AccessCredentials accessCredentials ) {
		this.accessCredentials = accessCredentials;
	}
	
	public Request getRequest() {
		Request request = null;
		switch( accessCredentials.getProtocol() ) {
		case http:
			try {
				request = httpExec();
			} catch (MalformedURLException e) {
				logger.error( "Invalid url detected for data source");
			}
			break;
		case https:
			request = httpsExec();
			break;
		case ssh:
			//add function to return a prepared ssh object
			break;
		case socket:
			//add function to return a prepared socket object
			break;
		
		}
		return request;
	}
	
	private HttpRequest httpExec() throws MalformedURLException {
		return new HttpRequest( buildParams( false ) );
	}
	
	private HttpsRequest httpsExec() {
		HttpsRequestParams params = (HttpsRequestParams) buildParams( true );
		//set any extra params needed for https right here
		return new HttpsRequest ( params );
	}
	
	private HttpRequestParams buildParams( boolean ssl ) {
		if( !(accessCredentials instanceof HTTPAccess ) ) return null;
		HTTPAccess haccess = (HTTPAccess) accessCredentials;
		HttpRequestParams params = ssl ? new HttpsRequestParams() : new HttpRequestParams();
		//logic to determine request type from HTTPAccess object
		boolean post = haccess.getGetVars() == null && haccess.getPostVars() != null;
		//setting request parameters based on request type
		params.setRequestType( post ? RequestType.POST : RequestType.GET )
			  .setPostGetVars( post ? haccess.getPostVars() : haccess.getGetVars() )
			  .setRequestURL( haccess.getAddress() );
		
		return params;
	}

}
