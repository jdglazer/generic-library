package com.jdglazer.dataflow.collector.crawler.parsers;

import java.util.ArrayList;
import java.util.List;

import com.jdglazer.dataflow.collector.parser.RegexParserBase;
import com.jdglazer.dataflow.collector.parser.models.RegexModel;
import com.jdglazer.web.crawler.WebCollectorParser;

import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlerRegexParser extends RegexParserBase implements WebCollectorParser {
	
	public CrawlerRegexParser( List<RegexModel> regexes, String dataSourceOutputPath ) {
		super( regexes, dataSourceOutputPath );
		//test code
		this.appendToOutputFile("testDataSource", "one,two,three,four\nfive,six,seven, eight");
	}
	
	public void parseCrawlerData(String contentType, WebURL url, ParseData parseData) {
		
		if( parseData instanceof HtmlParseData ) {
			String text = ( (HtmlParseData) parseData).getText();
			
			List<RegexModel> list = this.findMatches(text);
			
			for ( RegexModel r : list ) {
				
			}
		}
	}
	//test code
	public static void main( String [] args ) {
		new CrawlerRegexParser( new ArrayList<RegexModel>(), "/home/jglazer/Documents/my/folder");
	}
}
