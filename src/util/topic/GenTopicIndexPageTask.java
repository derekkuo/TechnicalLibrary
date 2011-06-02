package util.topic;

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

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.HtmlUtil;

public class GenTopicIndexPageTask extends TimerTask {
	private static boolean isRunning = false;
	private static ServletContext context;
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
		
		
		String htmlHead = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + "\n" +
		"<title>文章 - 技术资料库 Technical Library</title>"+  "\n" ;
//		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topic-index.css\">"+ "\n" +
//		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topbar.css\">"+ "\n" +
//		"<script type=\"text/javascript\" src=\"../js/jquery-1.4.3.min.js\"></script>"+ "\n" +
//		"<script type=\"text/javascript\" src=\"../js/jquery.tablesorter.js\"></script>"+ "\n" +
//		"<script type=\"text/javascript\" src=\"../js/techlib-topic-index.js\"></script>"+ "\n" +
//		"</head>"+ "\n" +
//		"<div id=\"techlib-body\">"+ "\n" +

		pw.write(htmlHead);


		InputStream is = context.getResourceAsStream("/common/techlib-htmlhead.html");
		HtmlUtil.printHtmlFromInputStream(pw, is);
		
		pw.println("<div id=\"techlib-head\"><h1>文章</h1></div>" +"\n" +
				"<div id=\"techlib-content\">文章总数： "+ allTopicHeader.size()
		);
		
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
		pw.println("<th scope=\"col\"></th><th scope=\"col\" width=\"480px\">标题<span class=\"techlib-little-gray\">（点击排序）</span></th><th scope=\"col\">类别</th><th scope=\"col\">发布日期</th>");
		pw.println("</tr></thead><tbody>");
		int topicId = 0;
		while(it.hasNext()){
			TopicHeader topicHeader = it.next();
			topicHeader.setPath(topicHeader.getPath().substring(7,topicHeader.getPath().length()));
			if(topicHeader.getPath().indexOf("index.html") >-1)
				continue;
			pw.println("<tr>");
//			topicId++;
//			pw.println("<td class=\"row\">");
//			pw.println(topicId);
//			pw.println("</td>");

			pw.println("<td class=\"row\">");
			pw.println("</td>");
			
			
			StringBuffer tagsSB = new StringBuffer();
			StringBuffer styleTagsSB = new StringBuffer();
			if(topicHeader.getTags().size()>0){
				Iterator tagsIt = topicHeader.getTags().iterator();
				while(tagsIt.hasNext()){
					String tag = ((String)(tagsIt.next())).trim();
					tagsSB.append( tag  + " ");
					styleTagsSB.append( "<a class=\"techlib-a-topicTag\" href=\"search?topictag="+tag+"\">"+tag+"</a>&nbsp;" );
				}
			}else{
				tagsSB.append("&nbsp;");
			}

			
			
			pw.println("<td class=\"row\">");
			pw.println( "<span class=\"techlib-topic-list-title\"><a href=\""+topicHeader.getPath()+"index.html\""+" title=\""
					+topicHeader.getPath()+"index.html\">"+topicHeader.getTitle()+"</a></span><br/>"
					+"<div>"
					//+"<span class=\"techlib-topic-list-author\">作者："+topicHeader.getAuthor()+"</span>"
					+"<span style=\"float:left\" class=\"techlib-topic-list-tags\">"
					+"标签："
					+styleTagsSB.toString()+"</span>"+"</div>");
			pw.println("</td>");



			String titleTagA = "<a class=\"topicFirstTag\" href=\"search?topictag="+topicHeader.getTags().get(0)+"\">"+topicHeader.getTags().get(0)+"</a>";
			pw.println("<td class=\"row\">");
			pw.println(titleTagA);
			pw.println("</td>");

			pw.println("<td class=\"row\">");
			if( !topicHeader.getSubmitDate().equals(""))
				pw.println("<span class=\"techlib-little-gray\">"+topicHeader.getSubmitDate()+"</span>"
						+"<br/><span class=\"techlib-topic-list-author\">"+topicHeader.getAuthor()+"</span>"	
				);
			else
				pw.println("<span class=\"techlib-little-gray\">"+"&nbsp;"+"</span>"
						+"<br/><span class=\"techlib-topic-list-author\">"+topicHeader.getAuthor()+"</span>"	
				);
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