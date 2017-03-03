package com.jdglazer.dataflow.collector;

import java.util.Map.Entry;
import java.util.Set;

public class Main {
	
	private static volatile boolean running = true;
	
	private static volatile boolean gracefullyStopped = true;
	
	private static DataSourceThreadManager dataSourceThreadManager;
	
	public static void main(String[] args) {
		//Register application shut down hook
		Runtime.getRuntime().addShutdownHook( new Main.DataCollectorShutdown() );
		
		while( running || !gracefullyStopped ) {
			try {
				if( gracefullyStopped ) {
				//code to restart because we're running and gracefully stopped
					//Define location and get all registered data sources
					DataSourceDefinitionManager.addDatasourceFolder("/home/jglazer/Documents/datasources");
					Set<Entry<String, DataSource>> sources = DataSourceDefinitionManager.getDefinedDataSources().entrySet();
					
					
					//register data sources with thread manager
					dataSourceThreadManager = new DataSourceThreadManager();
					for( Entry<String, DataSource> entry : sources ) {
						dataSourceThreadManager.addDataSource( entry.getValue() );
					}
					gracefullyStopped = false;
				}
				else if( !running ) {
					dataSourceThreadManager.removeAll();
					while( dataSourceThreadManager.getStagedForRemovalThreadCount() > 0 ) {
						dataSourceThreadManager.cleanSourcesToRemove();
						try{
							//We keep a sleep interval shorter than the minimum allowed sleep 
							//time on data collection threads. This ensures that cleanSourcesToRemove 
							//function doesn't miss the opportunity to kill collection threads when 
							//they are sleeping
							Thread.sleep(500);
						} catch( Exception e ) {}
					}
					gracefullyStopped = true;
				}				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static synchronized void stop() {
		running = false;
	}
	
	public static boolean fullyStopped() {
		return !running && gracefullyStopped;
	}

	private static class DataCollectorShutdown extends Thread {
		@Override 
		public void run() {
			Main.stop();
			while( !Main.fullyStopped() ) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}

