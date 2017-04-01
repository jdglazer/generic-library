package com.jdglazer.dataflow.collector;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	private static final String APPLICATION_CONFIG = "com/jdglazer/dataflow/collector/config/remote-data-collector.xml";
	private static final String APPLICATION_MAIN = "remote-data-collector.application";
	
	public static void main( String [] args ) {
		
		ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext( APPLICATION_CONFIG );
		RemoteDataCollector remoteDataCollector = (RemoteDataCollector) application.getBean(APPLICATION_MAIN);
		remoteDataCollector.start();
		
	}

}
