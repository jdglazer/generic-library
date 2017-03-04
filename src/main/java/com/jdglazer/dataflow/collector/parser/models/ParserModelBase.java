package com.jdglazer.dataflow.collector.parser.models;

import java.io.Serializable;

public class ParserModelBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Tells the parser where to write output from the parser to
	private String outputFileDirectory;
	//The languange that the parser is written in
	private Language language;
	//The path to the parser file
	private String parserFilePath;
	
	public enum Language implements Serializable {
		java, bash, none, regex
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getParserFilePath() {
		return parserFilePath;
	}

	public void setParserFilePath(String parserFilePath) {
		this.parserFilePath = parserFilePath;
	}

	public String getOutputFileDirectory() {
		return outputFileDirectory;
	}

	public void setOutputFileDirectory(String outputFileDirectory) {
		this.outputFileDirectory = outputFileDirectory;
	}
	
	@Override
	public boolean equals( Object parserModelBase ) {
		if( parserModelBase == null ) return false;
		if( !( parserModelBase instanceof ParserModelBase ) ) return false;
		ParserModelBase pmb = (ParserModelBase) parserModelBase;
		return pmb.getLanguage().equals( this.language ) && pmb.getParserFilePath().equals( this.parserFilePath ) && pmb.getOutputFileDirectory().equals( this.outputFileDirectory );
	}
}
