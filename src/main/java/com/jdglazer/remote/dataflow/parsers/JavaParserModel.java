package com.jdglazer.remote.dataflow.parsers;

public class JavaParserModel extends ParserModelBase {
	//The base class/interface that all java parsers must extend
	private static final Class requiredParserBase = JavaParserModel.class;
	
	private Class parser;

	public static Class getParserbase() {
		return requiredParserBase;
	}

	public Class getParser() {
		return parser;
	}

	public void setParser(Class parser) {
		this.parser = parser;
	}
	
}
