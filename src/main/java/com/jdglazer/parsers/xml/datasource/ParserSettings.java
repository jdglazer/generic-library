/**
 * A class to store environment variables necessary for selection and parsing of datafiles 
 */

package com.jdglazer.parsers.xml.datasource;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserSettings {
	
	Logger logger = LoggerFactory.getLogger( ParserSettings.class );
	
	private String schemaDir = "";
	private String schemaFile = "";
	private ArrayList< String > datasourceDefinitionDirectories;
	
	public ParserSettings() {
		
		datasourceDefinitionDirectories = new ArrayList< String >();
		setSchemaDir( loadEnvironmentVariable( "DATASOURCE_SCHEMA_DIR") );
		setSchemaFile( loadEnvironmentVariable( "DATASOURCE_SCHEMA_FILE") );
		
		String dirs = loadEnvironmentVariable( "DATASOURCE_DEFINITION_FOLDERS" );
		setDatasourceDefinitionDirectories( ( dirs != null ? dirs : "").split(":") );
		
	}
	
	private String loadEnvironmentVariable(String variable) {
		return System.getenv().get(variable);
	}
	
	public void setSchemaDir( String schemaDir ) {
		this.schemaDir = schemaDir;
	}
	
	public void setSchemaFile( String schemaFile ) {
		this.schemaFile = schemaFile;
	}
	
	public void setDatasourceDefinitionDirectories(String... directories) {
		for( String directory : directories ) {
			datasourceDefinitionDirectories.add(directory);
		}
	}
	
	public String getSchemaDir( ) {
		return schemaDir;
	}
	
	public String getSchemaFile( ) {
		return schemaFile;
	}
	
	public ArrayList<String> getDatasourceDefinitionDirectories(String... directories) {
		return datasourceDefinitionDirectories;
	}
}
