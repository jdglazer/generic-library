/**
 * A class to encase the collection from a single DataSource in a single thread
 */
package com.jdglazer.dataflow.collector;

import java.util.Date;

class DataSourceThreadHandler implements Runnable {
	
	private DataSource datasource;
	
	private boolean running = true;
	
	private boolean parseInProgress = false;
	
	public DataSourceThreadHandler( DataSource datasource ) {
		this.datasource = datasource;
	}
	
	public void run() {
		while( running ) {
			parseInProgress = true;
			//Code to perform data collection
			Date d = new Date( System.currentTimeMillis() );
			System.out.println(  ""+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"  Running collection for data source: " + datasource.getName() );
			try {
				parseInProgress = false;
				Thread.sleep( (long) datasource.getUpdateInterval() );
			} catch (InterruptedException e) {
				//log thread error
				running = false;
			}
		}
	}
	
	public void keepCollecting() {
		running = true;
	}
	
	public void stopCollecting() {
		running = false;
	}
	
	public boolean parseInProgress() {
		return parseInProgress;
	}
	
	public DataSource getDataSource() {
		return datasource;
	}
}
