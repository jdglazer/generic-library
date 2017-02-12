//NEEDS TESTING ON A LINUX SYSTEM
package com.jdglazer.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ProcessExecutor {
	
	public static final int ERROR_EXECUTING = 123456;
	
	private File scriptPath;
	
	private String arguments;
	
	private InputStream inputStream;
	
	private ProcessBuilder pb;
	
	public ProcessExecutor() {
		pb = new ProcessBuilder();
	}
	
	public int execute() {
		inputStream = null;
		pb.command(scriptPath.getAbsolutePath().trim()+" "+arguments.trim() );
				
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
	
	public int execute( File file ) {
		if( !file.isFile() ) {
			try {
				file.createNewFile();
			} catch (IOException e) {}
		}
		pb.redirectOutput( file );
		return execute();
	}
	
	public ProcessExecutor setScriptPath( String scriptPath ) {
		this.scriptPath = new File( scriptPath );
		return this;
	}
	
	public File getScriptPath() {
		return scriptPath;
	}
	
	public ProcessExecutor setArguments( String arguments ) {
		this.arguments = arguments;
		return this;
	}
	
	public String getArguments() {
		return arguments;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

}
