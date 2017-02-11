package com.jdglazer.dataflow.collector.parsers;

import java.io.Serializable;

public class JavaParserModel extends ParserModelBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
