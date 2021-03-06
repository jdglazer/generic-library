package com.jdglazer.dataflow.collector.communicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdglazer.dataflow.collector.DataSource;
import com.jdglazer.dataflow.collector.DataSource.Protocol;
import com.jdglazer.dataflow.collector.access.HTTPAccess;
import com.jdglazer.dataflow.collector.crawler.Crawler;
import com.jdglazer.dataflow.collector.crawler.parsers.CrawlerRegexParser;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase.Language;
import com.jdglazer.dataflow.collector.parser.models.RegexParserModel;
import com.jdglazer.web.crawler.WebCollectorConfig;
import com.jdglazer.web.crawler.WebCollectorParser;

import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class DataSourceCommunicationHub {
	
	Logger logger = LoggerFactory.getLogger( DataSourceCommunicationHub.class );
	
	private DataSource dataSource;
	
	private CommunicatorBase parserBase;
	
	public DataSourceCommunicationHub( DataSource dataSource ) {
		this.dataSource = dataSource;
	}
	
	private void init( int collectionId ) {
		Protocol protocol = dataSource.getAccess().getProtocol();
		
		switch( protocol ) {
		case http:
			;
		case https:
			try {
				parserBase = createCrawler();
			} catch (Exception e) {
				logger.debug("Failed to build an instance of the web crawler.");
			}
			break;
		case socket:
			//to add code soon
			break;
		case ssh:
			//to add code soon
			break;
		}
	}
	
	private WebCrawlCommunicator createCrawler() throws Exception {
		WebCollectorConfig config;
		HTTPAccess access = (HTTPAccess) dataSource.getAccess();
		ParserModelBase parserBase = dataSource.getDatasourceParser();
		Language language = parserBase.getLanguage();
		
		config = new WebCollectorConfig( access.getAddress() );
		config.setStorageFolder( parserBase.getOutputFileDirectory() );
		
		switch( language ) {
		case regex:
			if( parserBase instanceof RegexParserModel ) {
				CrawlerRegexParser regexParser = new CrawlerRegexParser( 
						( (RegexParserModel) parserBase ).getRegexList(), 
						parserBase.getOutputFileDirectory() );
				config.setParser( regexParser );
			}
		case bash:
			break;
		case java:
			break;
		default:
			break;
		}
		Crawler crawler = access.getCrawler();
		if( crawler != null ) {
			config.setMaxDepth( crawler.getMaxDepth() );
			config.setMaxPages( crawler.getMaxPageCount() );
			config.setMaxPageSize( crawler.getMaxPageSize() );
			config.setUrlRegexes( crawler.getUrlRegexes() );
		}
		
		
		WebCrawlCommunicator webCrawl = new WebCrawlCommunicator( config );
		return webCrawl;
	}
	
	//private void 
	
	public void execute(int collectionId) {
		init(collectionId);
		if( parserBase instanceof WebCrawlCommunicator ) {
			( (WebCrawlCommunicator) parserBase ).start();
		}
	}
} 
