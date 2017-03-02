/**
 * This is the class where all data source threads are referenced.
 * An instance of this class will provide access to all threads and their associated data sources
 */

package com.jdglazer.dataflow.collector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceThreadManager {
	Logger logger = LoggerFactory.getLogger( DataSourceThreadManager.class );
	/**
	 * Contains all the active data source threads and runnables
	 */
	private volatile static HashMap<String, ActiveDataSource> activeSources = new HashMap<String, ActiveDataSource>();
	/**
     * For data sources the we want to take out of active status and set for removal.
     * This list will periodically be cleaned of threads that are set to no longer running.
     * We need this as a staging area for threads that have been set to stop collecting, but 
     * are still running. We don't want to kill a thread in the middle of a collection
	 */
	private volatile static ArrayList<ActiveDataSource> sourcesToRemove = new ArrayList<ActiveDataSource>();
	
	/**
	 * Adds a data source
	 * @param dataSource
	 * @return
	 */
	public synchronized boolean addDataSource( DataSource dataSource ) {
		if ( dataSource == null )
			return false;
		String name = dataSource.getName();
		if ( name != null ) {
			if( !activeSources.containsKey( name ) ) {
				ActiveDataSource activeDataSource = new ActiveDataSource( new DataSourceThreadHandler( dataSource ) );
				//We want to make sure we catch and log any failures to start the thread
				try {
					activeDataSource.getDataSourceThreadHandler().keepCollecting();
					activeDataSource.getThread().start();
				} catch( IllegalThreadStateException itse ) {
					
				}
				activeSources.put(name, activeDataSource );
				return true;
			} else {
				logger.debug( "Data source thread of name "+name+" already exists in");
			}
			
		} else {
			logger.error("Can not add data source. Name not set for data source");	
		}
		
		return false;
	}
	
	/**
	 * Removes data sources from activity and gives them the order to stop collection.
	 * This is the only method used to remove data sources from active collection state
	 * @param dataSourceName
	 * @return
	 */
	public synchronized boolean removeDataSource( String dataSourceName ) {
		if( dataSourceName != null ) {
			ActiveDataSource ads = activeSources.remove( dataSourceName );
			if( ads != null) {
				if( !ads.getThread().getState().equals(Thread.State.TERMINATED) ) {
					ads.getDataSourceThreadHandler().stopCollecting();
					sourcesToRemove.add( ads );
				}
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Stages all threads for graceful shutdown and removal
	 */
	public synchronized void removeAll() {
		for( String key: activeSources.keySet() ) {
			removeDataSource( key );
		}
	}
	
/**
 * Cleans out the data sources staged for removal. Sleeping threads are interrupted and terminated threads are removed
 * from staging
 */
	public synchronized void cleanSourcesToRemove() {
		for( int i = 0; i < sourcesToRemove.size() ; i++  ) {
			ActiveDataSource ads = sourcesToRemove.get(i);
			//We want to first interrupt sleeping thread
			if( ads.getThread().getState().equals( Thread.State.TIMED_WAITING ) ) {
				try {
					ads.getThread().interrupt();
				} catch( IllegalThreadStateException itse ) {
					logger.error( "Could not interrupt thread for a datasource");
				}
			}
			//We remove all threads that are terminated ( ie, dead threads )
			if( ads.getThread().getState().equals(Thread.State.TERMINATED) ) {
				sourcesToRemove.remove( ads );
				i--;
			}
		}
	}
	
	/**
	 * Gets the number of threads registered as active threads
	 * @return
	 */
	public int getActiveThreadCount() {
		return activeSources.size();
	}
	
	/**
	 * Gets the number of threads staged for removal
	 * @return
	 */
	public int getStagedForRemovalThreadCount() {
		return sourcesToRemove.size();
	}
}
