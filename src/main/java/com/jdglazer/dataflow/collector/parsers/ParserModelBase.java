package com.jdglazer.dataflow.collector.parsers;

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
		java, bash
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
}
