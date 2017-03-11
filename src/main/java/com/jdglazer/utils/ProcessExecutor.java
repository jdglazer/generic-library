//NEEDS TESTING ON A LINUX SYSTEM
package com.jdglazer.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ProcessExecutor {
	
	public static final int ERROR_EXECUTING = 123456;
		
	private InputStream inputStream;
	
	private ProcessBuilder pb;
	
	public ProcessExecutor() {
		pb = new ProcessBuilder();
	}
	
	public int execute( String... command ) {
		inputStream = null;
		pb.command(command);
				
		try {
			Process p = pb.start();
			int exitCode = p.waitFor();
			inputStream = p.getInputStream();
			pb = new ProcessBuilder();
			return exitCode;
		} 
		catch (IOException e) {}
		catch (InterruptedException e) {}
		
		return ERROR_EXECUTING;
	}
	
	public int execute( File file, String... command) {
		if( !file.isFile() ) {
			try {
				file.createNewFile();
			} catch (IOException e) {}
		}
		pb.redirectOutput( file );
		return execute(command);
	}

	public InputStream getInputStream() {
		return inputStream;
	}
}
