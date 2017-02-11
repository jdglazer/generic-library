package com.jdglazer.remote.dataflow.parsers;

import java.io.Serializable;

public class BashParserModel extends ParserModelBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dataOutputPath;

	public String getDataOutputPath() {
		return dataOutputPath;
	}

	public void setDataOutputPath(String dataOutputPath) {
		this.dataOutputPath = dataOutputPath;
	}
	
}
