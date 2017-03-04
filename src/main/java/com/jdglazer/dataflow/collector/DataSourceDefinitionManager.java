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
	
	//variables use to mark a specific data source as a newly added data source or a data source to remove
	public static final int ADD = 1;
	
	public static final int REMOVE = 2;
	
	
	protected static Logger logger = LoggerFactory.getLogger( DataSourceDefinitionManager.class );
	
	private static ArrayList<File> DATA_SOURCE_FILE_FOLDERS = new ArrayList<File>();
	
	/**
	 * A data source hash map of data source last found with the value being the location of the data source in the xml files
	 */
	private static Map<String, DataSourceLocation> lastFoundDataSources = new HashMap<String, DataSourceLocation>();
		
	/**
	 * Folder in which serialized data source objects are stored
	 */
	private static String SERIALIZED_DATA_SOURCES_FOLDER;
	/**
	 * Gets all data sources from xml files in the data source definition folder
	 * @return All data source names mapped to their respective data source objects
	 */
	public static Map<String, DataSource> getDefinedDataSources() {
		return parseXMLFiles( getXMLFiles() );
	}
	
	/**
	 * Determines a list of edited, removed, and/or added data sources since last sweep of data source definition files
	 */
	public static void /* Map<String, Integer> ( a map to return sha assoiated with ADD or REMOVE static variable) */ getEdittedDataSources() {
/*********************
 * Next place where work will begin.
 **********************/
	}
	
	/** 
	 * A helper function that registers the location of all found data sources under an sha-256 key.
	 * This function is designed to help determine which data sources have been newly added, removed, and/or edited
	 * @return A map of Data source definition locations in the file system associated to sha-256 keys
	 */
	private static Map<String, DataSourceLocation> getDataSourceUpdateList() {
		List<File> files = getXMLFiles();
		Map<String, DataSourceLocation> dsLocations = new HashMap<String, DataSourceLocation>();
		for( File file : files ) {
			try {
				DataSourceBuilder builder = new DataSourceBuilder( file );
				dsLocations.putAll( builder.getDataSourceShaList() );
			} catch ( Exception e ) {
			}
		}
		return dsLocations;
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
	
	public static void addDatasourceFolder( String folder ) {
		DATA_SOURCE_FILE_FOLDERS.add( new File( folder ) );
	}
	
	/**
	 * A helper class designed to store a value that directs the user to the location
	 * of a data source (the file, and the index in the set of data sources in the file)
	 * @author jglazer
	 *
	 */
	public static class DataSourceLocation {
		
		public File file;
		public int positionInFile;
		
		DataSourceLocation( File file, int positionInFile ) {
			this.file = file;
			this.positionInFile = positionInFile;
		}
	}
}
