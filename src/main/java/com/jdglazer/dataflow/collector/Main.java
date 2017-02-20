package com.jdglazer.dataflow.collector;

import com.jdglazer.dataflow.collector.communicate.DataSourceCommunicator;

public class Main {
	public static void main(String[] args) {
		DataSource source = DataSourceDefinitionManager.getDefinedDataSources().get("cnn-test");
		DataSourceCommunicator comm = new DataSourceCommunicator( source );
		comm.execute();
	}
}
