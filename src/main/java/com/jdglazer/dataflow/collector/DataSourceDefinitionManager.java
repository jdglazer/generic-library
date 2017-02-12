/**
 * 10 Feb 2017
 * 
 * A class that manages the file system folder containing data source 
 * definitions and maintains a list of DataSource objects based on the 
 * contents of this folder.
 * 
 * @author Glazer, Joshua D.
 */
package com.jdglazer.dataflow.collector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class DataSourceDefinitionManager {
	
	static Logger logger = LoggerFactory.getLogger( DataSourceDefinitionManager.class );
	
	private static File [] DATA_SOURCE_FILE_FOLDERS = new File[]{ new File("/var/data") };
		
	/**
	 * Folder in which serialized data source objects are stored
	 */
	private static String SERIALIZED_DATA_SOURCES_FOLDER;
		
	public static Map<String, DataSource> getDefinedDataSources() {
		return parseXMLFiles( getXMLFiles() );
	}
		
	private static List<File> getXMLFiles() {
		List<File> xmls = new ArrayList<File>();
		for( File f : DATA_SOURCE_FILE_FOLDERS ) {
			if( f.isDirectory() ) {
				File [] fileList = f.listFiles( new DataSourceXmlFilter() );
				for( File file : fileList )
					xmls.add( file );
			}
		}
		
		return xmls;
	}
	
	private static Map< String, DataSource> parseXMLFiles( List<File> files ) {
		Map< String, DataSource > masterDsMap = null;
		if( files != null ) {
			masterDsMap = new HashMap<String, DataSource>();
			for( File f : files ) {
				try {
					masterDsMap.putAll( new DataSourceBuilder( f ).build() );
				} catch (ParserConfigurationException e) {
					logger.debug("Parser Configuration error for data source definition file "+f);
				} catch (SAXException e) {
					logger.debug("SAX error for data source definition file "+f);
				} catch (IOException e) {
					logger.debug("IO error for data source definition file "+f);
				}
			}
		}
		
		return masterDsMap;
	}
}
