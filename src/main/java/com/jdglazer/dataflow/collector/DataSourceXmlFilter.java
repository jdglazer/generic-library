package com.jdglazer.dataflow.collector;

import java.io.File;
import java.io.FileFilter;

public class DataSourceXmlFilter implements FileFilter {
	
	public static String DATA_SOURCE_FILE_EXT = "xml";

	//we are only checking for the proper extension. Not efficient to begin parsing the 
	//file. We save error checks on file formatting for the actual data source parser
	public boolean accept(File file) {
		if( !file.isFile() )
			return false;
		
		String [] nameSegments = file.getName().split(".");
		
		if( nameSegments.length < 2 || !nameSegments[nameSegments.length -1].toLowerCase().equals(DATA_SOURCE_FILE_EXT) )
			return false;
		
		return true;
	}

}
