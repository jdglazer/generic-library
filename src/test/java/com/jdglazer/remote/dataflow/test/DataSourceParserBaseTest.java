package com.jdglazer.remote.dataflow.test;
import com.jdglazer.remote.dataflow.DataSourceParserBase;

public class DataSourceParserBaseTest implements DataSourceParserBase {

	public boolean rawDataIn(byte [] input) {
		
		return false;
	}

	public <T> T rawDataOut() {
		
		return null;
	}
	
	public void setEncoding( String encoding ) {
		
	}

}
