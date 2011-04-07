package util.topic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

public class GenTopicIndexPageTask extends TimerTask {
	private static boolean isRunning = false;
	private ServletContext context;
	private List<TopicHeader> allTopicHeader;

	public GenTopicIndexPageTask() {
	}
	public GenTopicIndexPageTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			System.out.println("生成topic/index.html .... ....");
			allTopicHeader = TopicUtil.getAllTopicHeader( context );
			String path = context.getInitParameter("topicIndexHtmlPath");
			GenTopicIndexPageTask gipt = new GenTopicIndexPageTask();
			gipt.writeHtml( path, allTopicHeader);
		}
	}
	
	public <E> void writeHtml(String path, List<E> list){
		File file=null;
		PrintWriter pw =null;
		List<TopicHeader> allTopicHeader = (List<TopicHeader>) list;
		try {
			file = new File( path );
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pw = new PrintWriter( file, "utf-8" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		
		String htmlHead = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
				"<title>Technical Library Topic List</title>"+
			"<style type=\"text/css\">"+
			"BODY,P,UL,LI,a,a:hover {font-size: 14px;}"+
				"H1{ font-size: 26px; font-weight: bold; margin-top:5px; margin-bottom: 2px;}"+
				"ul li{margin-bottom: 25px;}"+
				"#techlib-content{width:720px; margin-left:auto; margin-right:auto;}"+
			"</style></head>"+
			"<div id=\"techlib-content\"><h1>Technical Library Topic List</h1>";
		pw.write(htmlHead);
		pw.println("<ul>");
		Iterator<TopicHeader> it = allTopicHeader.iterator();
		while(it.hasNext()){
			TopicHeader th = it.next();
			th.setPath(th.getPath().substring(7,th.getPath().length()));
			if(th.getPath().indexOf("index.html") >-1)
				continue;
			pw.println("<li>");
			pw.println("<a href=\""+th.getPath()+"\">"+th.getPath()+"</a><br>");
			pw.println( "《"+th.getTitle()+"》" );
			if(!th.getAuthor().equals(""))
				pw.println( "作者："+th.getAuthor() );
			pw.println("</li>");
		}
		pw.println("</ul>");
		pw.write("</div>");
		pw.flush();
		pw.close();
	}
}