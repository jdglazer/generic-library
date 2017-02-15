package com.jdglazer.dataflow.collector.parser.models;

import java.util.List;

public class RegexParserModel extends ParserModelBase {
	
	private List<Regex> regexList;

	public List<Regex> getRegexList() {
		return regexList;
	}

	public void setRegexList(List<Regex> regexList) {
		this.regexList = regexList;
	}
	
	public static class Regex {
		private String regex;

		public String getRegex() {
			return regex;
		}

		public void setRegex(String regex) {
			this.regex = regex;
		}
	}
}
