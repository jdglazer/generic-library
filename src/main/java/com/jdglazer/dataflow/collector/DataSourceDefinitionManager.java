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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DataSourceDefinitionManager {
	
	//variables use to mark a specific data source as a newly added data source or a data source to remove
	public static final Integer ADD = 1;
	
	public static final Integer REMOVE = 2;
	
	
	protected static Logger logger = LoggerFactory.getLogger( DataSourceDefinitionManager.class );
	
	private static ArrayList<File> DATA_SOURCE_FILE_FOLDERS = new ArrayList<File>();
	
	/**
	 * A data source hash map of data source last found with the value being the location of the data source in the xml files
	 */
	private static ConcurrentHashMap<String, String> lastFoundDataSources = new ConcurrentHashMap<String, String>();
	
	/**
	 * Reflects the last calculated added, and removed data sources. The key is the sha of the data source, the 
	 * value is a data source object if the data source is for added or a data source name String if the data source 
	 * needs to be removed
	 */
	private static ConcurrentHashMap< String, Object > dataSourceUpdates = new ConcurrentHashMap<String, Object>();
	/**
	 * Folder in which serialized data source objects are stored
	 */
	private static String SERIALIZED_DATA_SOURCES_FOLDER;
	/**
	 * Gets all data sources from xml files in the data source definition folder
	 * use this function on start-up to return 
	 * @return All data source names mapped to their respective data source objects
	 */
	public static Map<String, DataSource> getDefinedDataSources() {
		synchronized( lastFoundDataSources ) {
			Map<String, DataSource> dataSources = parseXMLFiles( getXMLFiles() );
			lastFoundDataSources.clear();
			for( Entry<String, DataSource> entry : dataSources.entrySet() ) {
				lastFoundDataSources.put( entry.getValue().getXmlSha(), "a");
			}
			return dataSources;
		}
	}
	
	/**
	 * Determines a list of edited, removed, and/or added data sources since last sweep of data source definition files
	 */
	public static Map<String, Object> updateEdittedDataSources() {
		synchronized ( lastFoundDataSources ) {
			dataSourceUpdates.clear();
			Map<String, Node> dsMap = getDataSourceUpdateList();
			//first we search for newly registered data sources
			for( String shaHash : dsMap.keySet() ) {
				if( !lastFoundDataSources.containsKey( shaHash ) ) {
					dataSourceUpdates.put( shaHash, DataSourceBuilder.parseDataSource(dsMap.get( shaHash ) ) );
				}
			}
			//next we search for old data sources that are no longer registered
			for( String shaHash : lastFoundDataSources.keySet() ) {
				if( !dsMap.containsKey( shaHash ) ) {
					dataSourceUpdates.put( shaHash, REMOVE );
				}
			}
			lastFoundDataSources.clear();
			//populate lastFoundDataSources with new data
			for( String shaHash : dsMap.keySet() ) {
				lastFoundDataSources.put( shaHash, "a" );
			}
			
			return dataSourceUpdates;
		}
	}
	
	/** 
	 * A helper function that registers all found data source nodes under an sha-256 key.
	 * This function is designed to help determine which data sources have been newly added, removed, and/or edited
	 * @return A map of Data source nodes in the file system associated to sha-256 keys
	 */
	private static Map<String, Node> getDataSourceUpdateList() {
		List<File> files = getXMLFiles();
		Map<String, Node> dsLocations = new HashMap<String, Node>();
		for( File file : files ) {
			try {
				DataSourceBuilder builder = new DataSourceBuilder( file );
				dsLocations.putAll( builder.getDataSourceShaList() );
			} catch ( Exception e ) {
			}
		}
		return dsLocations;
	}
	/**
	 * A helper function to get a list of xml files in the data source definition folder
	 * @return A List of xml files
	 */
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
	
	/**
	 * Turns a list of xml files into a map of data sources mapped to the data source name
	 * @param files
	 * @return
	 */
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
	
	/**
	 * Add the folders which contain data source xml definitions
	 * @param folder A folder modeled by a File object
	 */
	public static void addDatasourceFolder( String folder ) {
		DATA_SOURCE_FILE_FOLDERS.add( new File( folder ) );
	}
	
}
