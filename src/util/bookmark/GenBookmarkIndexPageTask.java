package util.bookmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import util.topic.TopicHeader;

public class GenBookmarkIndexPageTask extends TimerTask {
	private static boolean isRunning = false;
	private ServletContext context;
	private String workspacePath;

	File file=null;
	PrintWriter pw =null;

	private Map<Long,Bookmark> bookmarks = new HashMap<Long,Bookmark>();
	
	public GenBookmarkIndexPageTask() {}
	public GenBookmarkIndexPageTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			System.out.println(new Date()+" 生成bookmark/index.html ........");
			workspacePath = context.getInitParameter( "workspacePath" );
			GenBookmarkIndexPageTask gbpt = new GenBookmarkIndexPageTask();

			parsexml(workspacePath+context.getContextPath()+"/WebContent/bookmark/bookmark.xml");
			
			String bookmarkIndexHtmlPath = workspacePath+context.getContextPath()+"/WebContent/bookmark/index.html";
			gbpt.writeBookmarkIndexHtml(bookmarkIndexHtmlPath, bookmarks);
		}
	}
	
	public <E> void writeBookmarkIndexHtml(String path, Map<Long, Bookmark> bookmarks){

		try {
			file = new File( path );
			pw = new PrintWriter( file, "utf-8" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		String htmlHead = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
		"<title>Technical Library Topic List</title>"+
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topic-index.css\"></link>"+
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topbar.css\"></link>"+
		"<script type=\"text/javascript\" src=\"../js/jquery-1.4.3.min.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../js/jquery.tablesorter.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../js/techlib-topic-index.js\"></script>"+
		"</head>"+
		"<div id=\"techlib-content\">"+
		"<div id=\"topbar\"><strong>技术资料库 Technical Library</strong>&nbsp;&nbsp;<a href=\"../index.html\">首页|Home</a>&nbsp;&nbsp;<a href=\"../topic/index.html\">文章|Topic</a>&nbsp;&nbsp;<a href=\"../bookmark/index.html\">书签|Bookmark</a></div>"+
		"<div id=\"techlib-head\"><h1>精华资源书签 Bookmark</h1></div>";
		pw.write(htmlHead);
		
		writeTableStyle(bookmarks);
		
		pw.write("</div>");
		pw.flush();
		pw.close();
	}

	public void writeTableStyle( Map<Long, Bookmark> bookmarks ){
		Set set = bookmarks.keySet();
		List<Long> keylist = new ArrayList<Long>(set);
		Collections.sort(keylist);
		pw.println("<table id=\"mytable\" cellspacing=\"0\">");

		pw.println("<thead><tr>");
		pw.println("<th scope=\"col\">编号</th><th scope=\"col\">类别</th><th scope=\"col\">标题</th><th scope=\"col\">添加人</th>");
		pw.println("</tr></thead><tbody>");
		
		for(int i=0; i<keylist.size(); i++){
			Bookmark bookmark = bookmarks.get(keylist.get(i));
			
			pw.println("<tr>");
			pw.println("<td class=\"row\">");
			pw.println(bookmark.getId());
			pw.println("</td>");

			pw.println("<td class=\"row\">"+bookmark.getTechnicalType()+"</td>");
			
			StringBuffer tagsSB = new StringBuffer();
			if(bookmark.getTags().size()>0){
				Iterator tagsIt = bookmark.getTags().iterator();
				while(tagsIt.hasNext())
					tagsSB.append(((String)(tagsIt.next())).trim() + " ");
			}else{
				tagsSB.append("&nbsp;");
			}
			
		
			pw.println("<td class=\"row\">");
			if(bookmark.getSummary().equals(""))
				pw.println( "<a href=\""+bookmark.getUrl()+"\" title=\"技术标签："+tagsSB.toString()+"\">"+bookmark.getTitle()+"</a>" );
			else
				pw.println( "<a href=\""+bookmark.getUrl()+"\" title=\"技术标签："+tagsSB.toString()+"&nbsp;&nbsp;摘要："+bookmark.getSummary()+"\">"+bookmark.getTitle()+"</a>" );

			pw.println("</td>");
			
			pw.println("<td class=\"row\">");
			pw.println( bookmark.getProvider());

			pw.println("</td>");
		
			pw.println("</tr>");
		}
		pw.println("</tbody></table>");
	}

	public void parsexml(String filePath) {
		
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File( filePath ));
			List list = doc.selectNodes("/bookmarks/bookmark");
			for (int i = 0; i < list.size(); i++) {
				Element e1 = (Element) list.get(i);
				Element id = (Element) e1.selectSingleNode("id");
				Element url = (Element) e1.selectSingleNode("url");
				Element title = (Element) e1.selectSingleNode("title");
				Element resourceType = (Element) e1.selectSingleNode("resource-type");
				Element technicalType = (Element) e1.selectSingleNode("technical-type");
				Element provider = (Element) e1.selectSingleNode("provider");
				Element summary = (Element) e1.selectSingleNode("summary");
				Element tags = (Element) e1.selectSingleNode("tags");
				List<Element> tagElements = tags.elements();
				List<String> tagsList = new ArrayList<String>();
				for(int j=0; j<tagElements.size(); j++)
					tagsList.add( tagElements.get(j).getText() );
				Bookmark bookmark = new Bookmark(Long.parseLong(id.getText().trim()),
						url.getText().trim(),
						title.getText().trim(),
						resourceType.getText().trim(),
						technicalType.getText().trim(),
						tagsList,
						provider.getText().trim(),
						summary.getText().trim()
				); 
				bookmarks.put(Long.parseLong(id.getText()),bookmark);
//				System.out.println(bookmark);
				
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}