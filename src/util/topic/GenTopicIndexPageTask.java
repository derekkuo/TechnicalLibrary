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
				"<title>Technical Library Topic List</title>"+
			"<style type=\"text/css\">"+
			"BODY,P,UL,LI,a,a:hover,th,tr,td {font-size: 14px;}"+
				"H1{ font-size: 26px; font-weight: bold; margin-top:5px; margin-bottom: 2px;}"+
				"ul li{margin-bottom: 25px;}"+
				"#techlib-content,#techlib-head{width:720px; margin-left:auto; margin-right:auto; }"+
				"#techlib-content{padding-top:15px;}"+
				"a {height:20px; line-height:20px; color:#000; text-decoration: none; }"+
				"a:hover, .selected a{ background-position: right bottom; color:#999; }"+
			"</style></head>"+
			"<div id=\"techlib-head\"><h1>Technical Library Topic List</h1></div>"+
			"<div id=\"techlib-content\">";
		pw.write(htmlHead);
		
		//writeULStyle(allTopicHeader);
		writeTableStyle(allTopicHeader);
		
		pw.write("</div>");
		pw.flush();
		pw.close();
	}

	public void writeTableStyle(List<TopicHeader> allTopicHeader){
		pw.println("<table border=1px>");
		Iterator<TopicHeader> it = allTopicHeader.iterator();
		pw.println("<th>编号</th><th width=\"300px\">标题</th><th>作者</th><th width=\"170px\">标签</th>");
		int topicId = 0;
		while(it.hasNext()){
			TopicHeader th = it.next();
			th.setPath(th.getPath().substring(7,th.getPath().length()));
			if(th.getPath().indexOf("index.html") >-1)
				continue;
			pw.println("<tr>");
			topicId++;
			pw.println("<td>");
			pw.println(topicId);
			pw.println("</td>");
			
			
			pw.println("<td>");
			pw.println( "<a href=\""+th.getPath()+"\">"+th.getTitle()+"</a>" );
			pw.println("</td>");
			
			pw.println("<td>");
			pw.println( th.getAuthor().equals("")?"&nbsp;":th.getAuthor() );

			pw.println("</td>");
			
			pw.println("<td>");
			if(th.getTags().size()>0){
				Iterator tagsIt = th.getTags().iterator();
				while(tagsIt.hasNext())
					pw.write( ((String)(tagsIt.next())).trim() + " ");
			}else{
				pw.write("&nbsp;");
			}
			pw.println("</td>");
			
			pw.println("</tr>");
		}
		pw.println("</table>");
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