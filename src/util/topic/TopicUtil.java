package util.topic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TopicUtil {
	private static List<TopicHeader> allTopicHeader;
	private static Map<String, TopicTag> allTopicTags;
	
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
			
			if( path.indexOf(".html")>-1 )
				continue;
			try {
//				System.out.println(">>>>>>>>>>>>>>>"+path);
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
				topicHeader.setPath( path.substring(7,path.length()) );
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

	public static Map<String, TopicTag> getAllTopicTags() throws Exception{
		allTopicTags = new HashMap<String, TopicTag>();

		if(allTopicHeader == null)
			throw new Exception("请先调用TopicUtil.getAllTopicHeader(ServletContext context)");
		Iterator<TopicHeader> it = allTopicHeader.iterator();
		while(it.hasNext()){
			TopicHeader topicHeader = it.next();

			Iterator<String> tagsIt = topicHeader.getTags().iterator();
			TopicTag topicFirstTag = null;
			while(tagsIt.hasNext()){
				TopicTag topicTag = new TopicTag();
				topicTag.setName(tagsIt.next().trim().toLowerCase());
				topicTag.getTopicHeaders().add(topicHeader);
				boolean isMainTag = false;
				if(topicFirstTag == null){
					topicTag.setParentId(1);
					topicFirstTag = topicTag;
					isMainTag = true;
					saveTopicTagToMap(topicTag, topicHeader, isMainTag);
					continue;
				}
				topicFirstTag.getRelationTopicTags().add(topicTag);
				saveTopicTagToMap(topicTag, topicHeader, isMainTag);
			}

		}
		return allTopicTags;
	}
	
	public static void saveTopicTagToMap( TopicTag topicTag, TopicHeader topicHeader, boolean isMainTag ){
		if(allTopicTags.containsKey(topicTag.getName().trim().toLowerCase())){
			TopicTag existTopicTag = allTopicTags.get(topicTag.getName().trim().toLowerCase()); 
			existTopicTag.getTopicHeaders().add(topicHeader);
			existTopicTag.setTopicNum(existTopicTag.getTopicNum()+1);
			if(isMainTag)
				existTopicTag.setParentId(1);
		}
		else{
			topicTag.setTopicNum(1);
			allTopicTags.put(topicTag.getName().trim().toLowerCase(), topicTag);
		}
		
	}

	
	public static List getTopicTagsByName(String tagName){
		//TODO 返回指定的技术标签
		return null;
	}
	
	
}
