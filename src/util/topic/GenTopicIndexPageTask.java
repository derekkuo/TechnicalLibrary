package util.topic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GenTopicIndexPageTask extends TimerTask {
	private static boolean isRunning = false;
	private ServletContext context;
	private String workspacePath;
	File file=null;
	PrintWriter pw =null;
	
	public GenTopicIndexPageTask() {}
	public GenTopicIndexPageTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			System.out.println(new Date()+" 生成topic/index.html ........");
			workspacePath = context.getInitParameter( "workspacePath" );
			GenTopicIndexPageTask gipt = new GenTopicIndexPageTask();
			String topicIndexHtmlPath = workspacePath+context.getContextPath()+"/WebContent/topic/index.html";
			List<TopicHeader> allTopicHeader = TopicUtil.getAllTopicHeader( context );
			gipt.writeTopicIndexHtml( topicIndexHtmlPath, allTopicHeader );
		}
	}
	
	public <E> void writeTopicIndexHtml(String path, List<TopicHeader> allTopicHeader){

		try {
			file = new File( path );
			pw = new PrintWriter( file, "utf-8" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		String htmlHead = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
		"<title>原创技术文章 Topic</title>"+
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topic-index.css\">"+
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topbar.css\">"+
		"<script type=\"text/javascript\" src=\"../js/jquery-1.4.3.min.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../js/jquery.tablesorter.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../js/techlib-topic-index.js\"></script>"+
		"</head>"+
		"<div id=\"techlib-content\">"+
		"<div id=\"topbar\"><strong>技术资料库 Technical Library</strong>&nbsp;&nbsp;<a href=\"../index.html\">首页|Home</a>&nbsp;&nbsp;<a href=\"../topic/index.html\">文章|Topic</a>&nbsp;&nbsp;<a href=\"../bookmark/index.html\">书签|Bookmark</a></div>"+
		"<div id=\"techlib-head\"><h1>原创技术文章 Topic</h1></div>";
		pw.write(htmlHead);
		
		//writeULStyle(allTopicHeader);
		writeTableStyle(allTopicHeader);
		
		pw.write("</div>");
		pw.flush();
		pw.close();
	}

	public void writeTableStyle(List<TopicHeader> allTopicHeader){
		pw.println("<table id=\"mytable\" cellspacing=\"0\">");
		Iterator<TopicHeader> it = allTopicHeader.iterator();
		pw.println("<thead><tr>");
		pw.println("<th scope=\"col\">编号</th><th scope=\"col\">类别</th><th scope=\"col\">标题</th><th scope=\"col\">作者</th>");
		pw.println("</tr></thead><tbody>");
		int topicId = 0;
		while(it.hasNext()){
			TopicHeader topicHeader = it.next();
			topicHeader.setPath(topicHeader.getPath().substring(7,topicHeader.getPath().length()));
			if(topicHeader.getPath().indexOf("index.html") >-1)
				continue;
			pw.println("<tr>");
			topicId++;
			pw.println("<td class=\"row\">");
			pw.println(topicId);
			pw.println("</td>");
			

			StringBuffer tagsSB = new StringBuffer();
			if(topicHeader.getTags().size()>0){
				Iterator tagsIt = topicHeader.getTags().iterator();
				while(tagsIt.hasNext())
					tagsSB.append(((String)(tagsIt.next())).trim() + " ");
			}else{
				tagsSB.append("&nbsp;");
			}
			String titleTagA = "<a class=\"topicFirstTag\" href=\"search?topictag="+topicHeader.getTags().get(0)+"\">"+topicHeader.getTags().get(0)+"</a>";
			pw.println("<td class=\"row\">");
			pw.println(titleTagA);
			pw.println("</td>");
			
			pw.println("<td class=\"row\">");
//			pw.println( "<a href=\""+th.getPath()+"index.html\""+" title=\""+th.getSummary()+"\">"+th.getTitle()+"</a>" );
			pw.println( "<a href=\""+topicHeader.getPath()+"index.html\""+" title=\"技术标签："+tagsSB.toString()+"\">"+topicHeader.getTitle()+"</a>" );
			pw.println("</td>");
			
			pw.println("<td class=\"row\">");
			pw.println( topicHeader.getAuthor().equals("")?"&nbsp;":topicHeader.getAuthor() );

			pw.println("</td>");
		
			pw.println("</tr>");
		}
		pw.println("</tbody></table>");
	}
	
	public void writeULStyle(List<TopicHeader> allTopicHeader){
		pw.println("<ul>");
		Iterator<TopicHeader> it = allTopicHeader.iterator();
		while(it.hasNext()){
			TopicHeader th = it.next();
			th.setPath(th.getPath().substring(7,th.getPath().length()));
			if(th.getPath().indexOf("index.html") >-1)
				continue;
			pw.println("<li>");
			pw.println("<a href=\""+th.getPath()+"\">"+th.getPath()+"</a><br>");
			if(!th.getTitle().equals(""))
				pw.println( "《"+th.getTitle()+"》" );
			if(!th.getAuthor().equals("")){
				pw.println( "作者："+th.getAuthor() );
				pw.println( "<br>" );
			}
			if(th.getTags().size()>0){
				pw.write( "标签：" );
				Iterator tagsIt = th.getTags().iterator();
				while(tagsIt.hasNext())
					pw.write( ((String)(tagsIt.next())).trim() + " ");
			}
				
			pw.println("</li>");
		}
		pw.println("</ul>");
	}
}