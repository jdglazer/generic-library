package com.jdglazer.dataflow.collector.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jdglazer.dataflow.collector.parser.models.RegexModel;

public abstract class RegexParserBase extends ParserBase {
	
	private List<RegexModel> regexes;
	
	public RegexParserBase( List<RegexModel> regexes, String dataSourceOutputPath ) {
		super( dataSourceOutputPath );
		this.setRegexes(regexes);
	}
	
	protected List<RegexModel> findMatches( String text ) {
		List<RegexModel> founds = new ArrayList<RegexModel>();
		Pattern pattern = null;
		Matcher matcher = null;
		for( RegexModel r : regexes ) {
			try {
				pattern = Pattern.compile( r.getRegex() );
				matcher = pattern.matcher(text);
				if( matcher.find() ) {
					founds.add(r);
				}
			} catch( Exception e ){}
		}
		
		return regexes;
	}
	protected List<RegexModel> getRegexes() {
		return regexes;
	}

	protected void setRegexes(List<RegexModel> regexes) {
		this.regexes = regexes;
	}	
}
