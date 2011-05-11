package util.oa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import util.HtmlUtil;
import util.topic.GenTopicIndexPageTask;
import util.topic.TopicHeader;
import util.topic.TopicUtil;

public class GenOAQCIndexPageTask extends TimerTask {
	private static boolean isRunning = false;
	private static ServletContext context;
	private String workspacePath;
	File file=null;
	PrintWriter pw =null;
	
	public GenOAQCIndexPageTask() {}
	public GenOAQCIndexPageTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			System.out.println(new Date()+" 生成oa/qc/index.html ........");
			workspacePath = context.getInitParameter( "workspacePath" );
			GenOAQCIndexPageTask gipt = new GenOAQCIndexPageTask();
			String topicIndexHtmlPath = workspacePath+context.getContextPath()+"/WebContent/oa/qc/index.html";
			List<TopicHeader> allTopicHeader = TopicUtil.getAllTopicHeader( context, "/oa/qc" );
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
		
		
		String htmlHead = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + "\n" +
		"<title>文章 - 技术资料库 Technical Library</title>"+  "\n" ;

		pw.write(htmlHead);


		InputStream is = context.getResourceAsStream("/oa/common/techlib-htmlhead-oaqc.html");
		HtmlUtil.printHtmlFromInputStream(pw, is);
		
		pw.println("<div id=\"techlib-head\"><h1>文章</h1></div>"+ "\n" +
				"<div id=\"techlib-content\">");
		
		//writeULStyle(allTopicHeader);
		writeTableStyle(allTopicHeader);
		
		pw.write("</div></div>");
		pw.flush();
		pw.close();
	}

	public void writeTableStyle(List<TopicHeader> allTopicHeader){
		pw.println("<table id=\"mytable\" cellspacing=\"0\">");
		Iterator<TopicHeader> it = allTopicHeader.iterator();
		pw.println("<thead><tr>");
		pw.println("<th scope=\"col\"></th><th scope=\"col\" width=\"480px\">标题</th>");
		pw.println("</tr></thead><tbody>");
		int topicId = 0;
		while(it.hasNext()){
			TopicHeader topicHeader = it.next();
			topicHeader.setPath(topicHeader.getPath().substring(7,topicHeader.getPath().length()));
			if(topicHeader.getPath().indexOf("index.html") >-1)
				continue;
			pw.println("<tr>");

			pw.println("<td class=\"row\">");
			pw.println("</td>");
			pw.println("<td class=\"row\">");
			pw.println( "<span class=\"techlib-topic-list-title\"><a target=\"_blank\" href=\""+topicHeader.getPath()+"\""+" title=\""
					+topicHeader.getPath()+"\">"+topicHeader.getTitle()+"</a></span><br/>"
					+"<div>");
			pw.println("</td>");



			pw.println("</tr>");
		}
		pw.println("</tbody></table>");
	}

}
