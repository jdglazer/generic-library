/**
 * A class to encase the collection from a single DataSource in a single thread
 */
package com.jdglazer.dataflow.collector;

import java.util.Date;

import com.jdglazer.dataflow.collector.communicate.DataSourceCommunicationHub;

class DataSourceThreadHandler implements Runnable {
	
	private DataSource datasource;	
	
	private DataSourceCommunicationHub dataSourceCommunicator;
	
	private boolean running;
	
	private static int collectionId = 0;
	
	public DataSourceThreadHandler( DataSource datasource ) {
		this.datasource = datasource;
		this.dataSourceCommunicator = new DataSourceCommunicationHub( datasource );
		this.running = false;
	}
	
	public void run() {
		while( running ) {
			Date d = new Date( System.currentTimeMillis() );
			short hourOfWeek = (short) (d.getDay()*24 + d.getHours() );
			if( hourOfWeek >= datasource.getAliveIntervals().get(0)[0] 
					&& hourOfWeek <= datasource.getAliveIntervals().get(0)[1]) {
				collectionId++;
				//Code to perform data collection
				System.out.println(  ""+d.getHours()+":"+d.getMinutes()+":"+(d.getSeconds() <10 ? "0":"" )+d.getSeconds()+"  Running collection for data source: " + datasource.getName() );
				dataSourceCommunicator.execute(collectionId);
			} else {
				
			}
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
