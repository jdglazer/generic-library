package com.jdglazer.dataflow.collector.crawler.parsers;

import java.util.List;

import com.jdglazer.dataflow.collector.parser.models.RegexModel;
import com.jdglazer.web.crawler.WebCollectorParser;

import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlerRegexParser implements WebCollectorParser {
	
	private List<RegexModel> regexes;
	
	public CrawlerRegexParser( List<RegexModel> regexes ) {
		this.regexes = regexes;
	}
	
	public void parseCrawlerData(String contentType, WebURL url, ParseData parseData) {
		
	}

}
