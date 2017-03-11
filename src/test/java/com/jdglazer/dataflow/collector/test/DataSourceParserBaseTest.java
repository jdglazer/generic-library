package com.jdglazer.dataflow.collector.test;
import com.jdglazer.dataflow.collector.communicate.CommunicatorBase;

public class DataSourceParserBaseTest implements CommunicatorBase {

	public boolean rawDataIn(byte [] input) {
		
		return false;
	}

	public <T> T rawDataOut() {
		
		return null;
	}
	
	public void setEncoding( String encoding ) {
		
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

}
