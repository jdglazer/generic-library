package com.jdglazer.dataflow.collector.output;

import java.io.File;

import com.jdglazer.utils.TimeProvider;

public abstract class DataSourceOutputFormat {
	
	public static String outputFileNameProvider( String dataSourceName ) {
		return dataSourceName+"/"+TimeProvider.getFormattedDateTimeUTC()+".csv";
	}

	public static void main( String [] args ) {
		System.out.println( outputFileNameProvider("cnn-test") );
	}
}
