/**
 * A class that holds a reference to a data source Thread and its DataSourceThreadHandler Runnable.
 * Main function is to consolidate running data source functionality in one place.
 */
package com.jdglazer.dataflow.collector;

public class ActiveDataSource {
	
	private DataSourceThreadHandler dataSourceThreadHandler;
	
	private Thread thread;
	
	public ActiveDataSource( DataSourceThreadHandler dataSourceThreadHandler ) {
		this.dataSourceThreadHandler = dataSourceThreadHandler;
		thread = new Thread( dataSourceThreadHandler );
	}
	
	//We only allow getter methods. We do not want to allow a the thread to be overwritten while 
	//it's still running and thus be running without a handle to access it by
	public DataSourceThreadHandler getDataSourceThreadHandler() {
		return dataSourceThreadHandler;
	}	
	public Thread getThread() {
		return thread;
	}
}
