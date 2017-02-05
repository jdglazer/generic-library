package com.jdglazer.remote.dataflow.parsers;

public class ParserModelBase {
	
	//Tells the parser where to write output from the parser to
	private String outputFileDirectory;
	//The languange that the parser is written in
	private Language language;
	//The path to the parser file
	private String parserFilePath;
	
	public enum Language {
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
