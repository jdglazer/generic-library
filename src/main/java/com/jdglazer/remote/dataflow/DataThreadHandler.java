/**
 * A class to encase the collection from a single DataSource in a single thread
 */
package com.jdglazer.remote.dataflow;

class DataThreadHandler implements Runnable {
	
	private DataSource datasource;
	
	private boolean running = true;
	
	public DataThreadHandler( DataSource datasource ) {
		this.datasource = datasource;
	}
	
	public void run() {
		while( running ) {
			//Code to perform data collection
			try {
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
}
