package com.jdglazer.dataflow.collector.parser.models;

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
	
	@Override
	public boolean equals( Object javaParserModel ) {
		if( javaParserModel == null ) return false;
		if( super.equals(javaParserModel ) ) return false;
		JavaParserModel jpm = (JavaParserModel) javaParserModel;
		return jpm.getParser().equals( this.parser );
	}
}
