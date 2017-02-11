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
	
	Logger logger = LoggerFactory.getLogger( DataSourceDefinitionManager.class );
	
	//We need a list to store all valid data sources detected in folder
	private static Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
	
	//When a data source is removed from the file system folder xmls, we need to remove this 
	//data source from the our active sources List and stage it to be removed from collection
	//by way of the DataSourceThreadManager
	private static ArrayList<DataSource> dataSourcesStagedForRemoval = new ArrayList<DataSource>();
	
	/**
	 * All folders from which data source xmls are collected
	 */
	private static ArrayList<File> DATA_SOURCE_FILE_FOLDERS = new ArrayList<File>();
	
	/**
	 * Folder in which serialized datasource objects are stored
	 */
	private static String SERIALIZED_DATA_SOURCES_FOLDER;
	
	private synchronized void addDataSourceForCollection( DataSource datasource ) {
		if( !isDataSource( datasource.getName() ) )
			dataSources.put( datasource.getName(), datasource );
		else
			logger.debug( "Data source named "+datasource.getName()+" can not be added. It already exists." );
	}
	
	private synchronized void removeDataSourceFromCollection( String dataSourceName ) {
		DataSource removed = dataSources.remove( dataSourceName );
		dataSourcesStagedForRemoval.add( removed );

	}
	
	private boolean isDataSource( String dataSourceName ) {
		return dataSources.get( dataSourceName ) != null;
	}
	
	public synchronized void refreshDataSources() {
		//NEXT PLACE TO WORK ON THE CODE. ADD AN EQUALS FUNCTION TO THE DataSource class as well
		//We want our new DataSource Model to look like the data source list produced from the 
		//xml definition files. However, we don't want to replace already active data sources
		//with the same data source and we want to get rid of data sources from the list that 
		//are not found in our newly parsed list. We also want to add new data sources. Many
		//Things to accomplish in this function so THINK IT THROUGH!
		//NOTE: You may want to develop some protocol to tell the DataSourceThreadManager to 
		//either remove a thread entirely or remove and replace

	}
	
	private List<File> getXMLFiles() {
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
	
	private Map< String, DataSource> parseXMLFiles( List<File> files ) {
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
