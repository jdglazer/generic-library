/**
 * A class to encase the collection from a single DataSource in a single thread
 */
package com.jdglazer.dataflow.collector;

import java.util.Date;

class DataSourceThreadHandler implements Runnable {
	
	private DataSource datasource;	
	
	private boolean running;
	
	public DataSourceThreadHandler( DataSource datasource ) {
		this.datasource = datasource;
		this.running = false;
	}
	
	public void run() {
		while( running ) {
			//Code to perform data collection
			Date d = new Date( System.currentTimeMillis() );
			System.out.println(  ""+d.getHours()+":"+d.getMinutes()+":"+(d.getSeconds() <10 ? "0":"" )+d.getSeconds()+"  Running collection for data source: " + datasource.getName() );
			try {
				Thread.sleep( (long) datasource.getUpdateInterval() * 1000l );
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
	
	public DataSource getDataSource() {
		return datasource;
	}
}
