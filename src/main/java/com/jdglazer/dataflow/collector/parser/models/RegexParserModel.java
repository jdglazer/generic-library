package com.jdglazer.dataflow.collector.parser.models;

import java.util.List;

public class RegexParserModel extends ParserModelBase {
	
	private List<String> regexList;

	public List<String> getRegexList() {
		return regexList;
	}

	public void setRegexList(List<String> regexList) {
		this.regexList = regexList;
	}
	
}
