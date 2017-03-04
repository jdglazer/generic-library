package com.jdglazer.dataflow.collector.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdglazer.dataflow.collector.output.DataSourceOutputFormat;

public abstract class ParserBase {
	
	Logger logger = LoggerFactory.getLogger( ParserBase.class );
	
	private String eventOutputPathBase; 
	
	private String dataSourceName;
	
	/**
	 * The only constructor method. It requires a data source output path to be provided
	 * @param eventOutputPathBase The absolute base path for output data from data source collection
	 */
	protected ParserBase( String eventOutputPathBase ) {
		this.eventOutputPathBase = eventOutputPathBase;		
	}
	
	/**
	 * Writes a string to a file for the specified data sources
	 * 
	 * @param dataSourceName The name of the data source for which the output file will be created
	 * @param eventString The output data to add
	 * @return true if the writing of the files succeeded, false otherwise
	 */
	protected boolean appendToOutputFile( String dataSourceName, String eventString ) {
		File file = new File ( eventOutputPathBase+"/"+
									DataSourceOutputFormat.outputFileNameProvider(dataSourceName)
								  ),
			 parent = new File( file.getParent() );
		FileOutputStream fos = null;
		if( !parent.isDirectory() ? parent.mkdirs() : true ) {
			if( file.exists() ) {
				logger.debug("File, "+file+" already exists.");
			}
			else {
				try {
					if( file.createNewFile() ) {
						fos = new FileOutputStream( file );
						fos.write( ("\n"+eventString).getBytes() );
						try {
							fos.close();
						} catch( Exception e ){
							logger.warn("Failed to close file: "+file);
						}
						return true;
					}
					else {
						throw new IOException();
					}
				} catch (FileNotFoundException e) {
					logger.error("Failed to find the newly created file: "+file);
				} catch (IOException e) {
					logger.error( "Failed to create or write to file, "+file+" for data source output");
				}
			}
		}
		if( fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				logger.warn( "Failed to close file: "+file );
			}
		}
		return false;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}
	
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
}