package util.topic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TopicUtil {
	private static List<TopicHeader> allTopicHeader;
	
	public static List<TopicHeader> getAllTopicHeader(ServletContext context) {
		if(allTopicHeader==null)
			allTopicHeader = new ArrayList<TopicHeader>();
		else
			allTopicHeader.clear();

		Document document = null; 
		Set topicSet = null;

		topicSet = context.getResourcePaths("/topic");
		InputStream is = null;
//		List topicList = new ArrayList(topicSet);
//		Collections.sort(topicList);
		Iterator<String> it = topicSet.iterator();
		while (it.hasNext()) {
			String path = it.next();
			
			if( path.indexOf("index.html")>-1 )
				continue;
			try {
				is = context.getResourceAsStream( path + "index.html" );
				document = util.HtmlUtil.getJsoupDocument( is );
				String title = document.select("#title").text();
				String subtitle = document.select("#subtitle").text();
				String author = document.select("#author").text();
				List<String> tags = new ArrayList<String>();
				Elements tagsElements = document.select("#tags > a");
				Iterator tagsElementIt = tagsElements.iterator();
				while(tagsElementIt.hasNext()){
					tags.add( ((Element)tagsElementIt.next()).text() );
				}
				String summary = document.select("p").first().text();
				Elements menu = document.select(".menu");
				String submitDate = document.select("#submitDate").text();
				TopicHeader topicHeader = new TopicHeader(path, title, subtitle, author, tags, summary, menu, submitDate);
				//System.out.println("---TopicUtil.getAllTopicHeader()---"+topicHeader);
				allTopicHeader.add( topicHeader );
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		Collections.sort(allTopicHeader);
		return allTopicHeader;
	}

	public static List<TopicHeader> getAllTopicHeader(ServletContext context, String htmlPath) {
		if(allTopicHeader==null)
			allTopicHeader = new ArrayList<TopicHeader>();
		else
			allTopicHeader.clear();

		Document document = null; 
		Set topicSet = null;

		topicSet = context.getResourcePaths(htmlPath);
		InputStream is = null;
//		List topicList = new ArrayList(topicSet);
//		Collections.sort(topicList);
		Iterator<String> it = topicSet.iterator();
		while (it.hasNext()) {
			String path = it.next();
			
			if( path.indexOf("index.html")>-1 || !(path.indexOf(".html")>-1))
				continue;
			try {
				
				is = context.getResourceAsStream( path );
				document = util.HtmlUtil.getJsoupDocument( is );
				String title = document.select("title").text();
				String subtitle = document.select("#subtitle").text();
				String author = document.select("#author").text();
				List<String> tags = new ArrayList<String>();
				Elements tagsElements = document.select("#tags > a");
				Iterator tagsElementIt = tagsElements.iterator();
				while(tagsElementIt.hasNext()){
					tags.add( ((Element)tagsElementIt.next()).text() );
				}
//				String summary = document.select("p").first().text();
				String summary = "";
				Elements menu = document.select(".menu");
				String submitDate = document.select("#submitDate").text();
				TopicHeader topicHeader = new TopicHeader(path, title, subtitle, author, tags, summary, menu, submitDate);
				//System.out.println(topicHeader);
				allTopicHeader.add( topicHeader );
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		Collections.sort(allTopicHeader);
		return allTopicHeader;
	}

	
}
