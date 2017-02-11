package com.jdglazer.dataflow.utils.communicate.http;

import java.io.File;

public interface HttpResponse {
	
	public String readAsString();
	
	public byte [] readAsByteArray();
	
	public boolean readIntoFile( File file );
	
}
