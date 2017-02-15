package com.jdglazer.dataflow.collector.parser.models;

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
	
	@Override
	public boolean equals( Object bashParserModel ) {
		if( !super.equals( bashParserModel ) ) return false;
		if( !( bashParserModel instanceof BashParserModel ) ) return false;
		BashParserModel bpm = (BashParserModel) bashParserModel;
		return bpm.getDataOutputPath().equals( this.dataOutputPath );
	}
	
}
