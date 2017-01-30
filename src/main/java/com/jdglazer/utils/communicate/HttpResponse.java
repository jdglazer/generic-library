package com.jdglazer.utils.communicate;

import java.io.File;

public interface HttpResponse {
	
	public String readAsString();
	
	public byte [] readAsByteArray();
	
	public boolean readIntoFile( File file );
	
}
