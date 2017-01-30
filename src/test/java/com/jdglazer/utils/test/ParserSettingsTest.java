package com.jdglazer.utils.test;

import java.util.Map;

import com.jdglazer.parsers.xml.datasource.ParserSettings;

public class ParserSettingsTest {
	public static void main( String [] args ) {
		ParserSettings parserSettings = new ParserSettings();
		parserSettings.setDatasourceDefinitionDirectories("/home/joshua/Documents/datasources", "/etc/jdglazer/datasources", "etc/jdglazer/data");
		parserSettings.setSchemaDir("/var/jdglazer/schema");
		parserSettings.setSchemaFile("datasourceSchema");
		
		Map<String,String> map = System.getenv();
		
		String output = map.get("DATASOURCE_SCHEMA_DIR");
		output += ", "+map.get("DATASOURCE_SCHEMA_FILE");
		output += ", "+map.get("DATASOURCE_DEFINITION_FOLDERS");
		
		System.out.println( output );
		
	}
}
