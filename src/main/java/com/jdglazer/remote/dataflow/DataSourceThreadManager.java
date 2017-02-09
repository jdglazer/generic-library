/**
 * This is the class where all data source threads are referenced.
 * An instance of this class will provide access to all threads and their associated data sources
 */

package com.jdglazer.remote.dataflow;

import java.util.ArrayList;
import java.util.HashMap;

public class DataSourceThreadManager {
	/**
	 * Contains all the active data source threads
	 */
	HashMap<String, Thread> activeSources;
	/**
	 * For newly discovered data-sources that have yet to be verified as new/unique from any sources being 
	 * monitored in one of the activeSources threads
	 */
	ArrayList<DataSource> pendingSources;
	/**
	 * All folders from which data source xmls are collected
	 */
	private static ArrayList<String> DATA_SOURCE_FILE_FOLDERS;
	
	private static String SERIALIZED_DATA_SOURCES;
	
}
