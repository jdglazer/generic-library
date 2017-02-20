package com.jdglazer.dataflow.collector.communicate;

import com.jdglazer.dataflow.collector.DataSource;
import com.jdglazer.dataflow.collector.DataSource.Protocol;
import com.jdglazer.dataflow.collector.DataSourceParserBase;
import com.jdglazer.dataflow.collector.WebCrawl;
import com.jdglazer.dataflow.collector.access.HTTPAccess;
import com.jdglazer.dataflow.collector.crawlers.Crawler;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase.Language;
import com.jdglazer.dataflow.collector.parser.models.RegexParserModel;
import com.jdglazer.web.crawler.WebCollector;
import com.jdglazer.web.crawler.WebCollectorConfig;
import com.jdglazer.web.crawler.WebCollectorParser;

import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class DataSourceCommunicator {
	
	private DataSource dataSource;
	
	private DataSourceParserBase parserBase;
	
	public DataSourceCommunicator( DataSource dataSource ) {
		this.dataSource = dataSource;
		init();
	}
	
	private void init() {
		Protocol protocol = dataSource.getAccess().getProtocol();
		
		switch( protocol ) {
		case http:
			try {
				parserBase = createCrawler();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;
		case https:
			break;
		}
	}
	
	private WebCrawl createCrawler() throws Exception {
		WebCollectorConfig config;
		HTTPAccess access = (HTTPAccess) dataSource.getAccess();
		ParserModelBase parserBase = dataSource.getDatasourceParser();
		Language language = parserBase.getLanguage();
		
		config = new WebCollectorConfig( access.getAddress() );
		config.setStorageFolder( parserBase.getOutputFileDirectory() );
		
		switch( language ) {
		case none:
			config.setUrlRegexes( ( (RegexParserModel) parserBase).getRegexList() );
		}
		Crawler crawler = access.getCrawler();
		if( crawler != null ) {
			config.setMaxDepth( crawler.getMaxDepth() );
			config.setMaxPages( crawler.getMaxPageCount() );
			config.setMaxPageSize( crawler.getMaxPageSize() );
		}
		
		config.setParser( new ParseIt() );
		WebCrawl webCrawl = new WebCrawl( config );
		return webCrawl;
	}
	
	//private void 
	
	public void execute() {
		if( parserBase instanceof WebCrawl )
			( (WebCrawl) parserBase ).start();
	}
	
	public class ParseIt implements WebCollectorParser {

		public void parseCrawlerData(String contentType, WebURL url, ParseData parseData) {
			if(parseData instanceof HtmlParseData) {
				HtmlParseData data = (HtmlParseData) parseData;
				String text = data.getText();
				System.out.println(" "+text.length()+"  contains trump reference: "+text.toLowerCase().contains("donald trump")+" "+url.getURL());
			}
			
		}
		
	}
} 
