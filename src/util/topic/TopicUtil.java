package util.topic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

public class TopicUtil {
	private static List<TopicHeader> allTopicHeader;
	
	public static List<TopicHeader> getAllTopicHeader(ServletContext context) {
		if(allTopicHeader==null)
			allTopicHeader = new ArrayList<TopicHeader>();
		else
			allTopicHeader.clear();
		InputStream is = null;
		Set topicSet = null;

		topicSet = context.getResourcePaths("/topic");
		
		List topicList = new ArrayList(topicSet);
		Collections.sort(topicList);
		Iterator<String> it = topicList.iterator();
		while (it.hasNext()) {
			String path = it.next();
			if( path.indexOf("index.html")>-1 )
				continue;
			try {
				is = context.getResourceAsStream( path );

				String str;
				String titleLine = null;
				String authorLine = null;
				String title = null;
				String author = null;
				List<String> tags = new ArrayList<String>();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is, "utf-8"));
				while ((str = br.readLine()) != null) {
					if (str.indexOf("<title>") > -1)
						titleLine = str;
					if (str.indexOf("\"author\"") > -1) {
						authorLine = str;
					}
					if (str.indexOf("id=\"tags\"") >-1){
						while(true){
							str = br.readLine();
							if( str.indexOf("</span>") >-1 )
								break;
							tags.add(str);
						}
						break;
					}
					
				}
				
				if(titleLine!=null){
					try {
						title = titleLine.substring(
								titleLine.indexOf("<title>") + 7,
								titleLine.indexOf("</title>")).trim();
					} catch ( StringIndexOutOfBoundsException e) {
						title = null;
					}
				}
				if(authorLine!=null){
					try{
						author = authorLine.substring(
							authorLine.indexOf("\"author\"")+9,
							authorLine.indexOf("</span>")).trim();
						if(author.indexOf('>')==0)
							author = author.substring(1,author.length()).trim();
					}catch( StringIndexOutOfBoundsException e){
						author = null;						
					}
				}
				String tagStr;
				if(tags!=null){
					for(int i=0; i<tags.size(); i++){
						tagStr = tags.get(i);
						tagStr = tagStr.substring(tagStr.indexOf(">")+1, tagStr.indexOf("</"));
						tags.set(i, tagStr );
					}
				}
					
				title = title == null ? "" : title;
				author = author == null ? "" : author;
				allTopicHeader.add(new TopicHeader(path, title, author, tags));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return allTopicHeader;
	}
}
