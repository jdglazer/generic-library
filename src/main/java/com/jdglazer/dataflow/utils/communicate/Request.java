/**
 * Placeholder class which is extended by all request objects HttpRequest, SshRequest, SocketRequest, ect.
 */
package com.jdglazer.dataflow.utils.communicate;

public class Request {
	public String CLASS_NAME;
	public Request( String CLASS_NAME ) {
		this.CLASS_NAME = CLASS_NAME;
	}
}
