package com.jdglazer.dataflow.collector.parser.models;

import java.util.List;

public class RegexParserModel extends ParserModelBase {
	
	private List<RegexModel> regexList;

	public List<RegexModel> getRegexList() {
		return regexList;
	}

	public void setRegexList(List<RegexModel> regexList) {
		this.regexList = regexList;
	}

}
