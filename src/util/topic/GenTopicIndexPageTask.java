package util.topic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import util.HtmlUtil;
import util.pinyin.Pinyin4j;

public class GenTopicIndexPageTask extends TimerTask {
	private static final String TECHNOLOGY_MAIN_TAGS = "mainTags";
	private static final String TECHNOLOGY_ALL_TAGS = "allTags";
	private static final String AUTHOR_ALL_TAGS = "allAuthorTags";
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
			
			String basePath = workspacePath+context.getContextPath()+"/WebContent/topic/";
			Map<String, TopicTag> allTopicTags = null;
			try {
				allTopicTags = TopicUtil.getAllTopicTags();
			} catch (Exception e) {
				e.printStackTrace();
			}
			gipt.writeCategoryTopicIndexHtml( basePath, allTopicTags );
			
		}
	}
	
	public void writeCategoryTopicIndexHtml( String basePath, Map<String, TopicTag> allTopicTags ){
		
		Set allTopicTagsSet = allTopicTags.keySet();
		Iterator it = allTopicTagsSet.iterator();
		
		List<TopicHeader> showTopicHeaders = new ArrayList<TopicHeader>();
		while(it.hasNext()){
			TopicTag topicTag = (TopicTag)allTopicTags.get( it.next() );
//			System.out.println(topicTag.getName());
//			if(topicTag.getParentId()!=null && topicTag.getParentId()==1)
//			System.out.println(">>>>>>>>>>>>>>>>>"+topicTag.getTopicHeaders().size());
				writeTopicIndexHtml(basePath+"index-"+topicTag.getEnName()+".html", topicTag.getTopicHeaders());
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
		
		pw.println("<div id=\"techlib-head\"><h1>文章</h1></div>" 
//				+"<div id=\"techlib-content\">文章总数："+ allTopicHeader.size()
		);
		
		writeTagsStyle(allTopicHeader, TECHNOLOGY_MAIN_TAGS);
		
		//writeULStyle(allTopicHeader);
		writeTableStyle(allTopicHeader);
		
		pw.write("<div id=\"techlib-topic-index-bottom-tags\">");
//		pw.write("标签云");
		writeTagsStyle(allTopicHeader, TECHNOLOGY_ALL_TAGS);
		pw.write("</div>");
		
//		pw.write("<div id=\"techlib-topic-index-bottom-tags-author\">");
//		pw.write("标签云作者");
//		writeTagsStyle(allTopicHeader, AUTHOR_ALL_TAGS);
//		pw.write("</div>");		
		
		pw.write("</div></div>");
		pw.flush();
		pw.close();
	}

	public String writeTagsStyle(List<TopicHeader> allTopicHeader, String tagsStyle){
		Map<String, TopicTag> allTopicTags = null;
		StringBuffer sb = new StringBuffer();
		sb.append("<div id=\"techlib-topic-list-tagsline\"><span class=\"techlib-topic-list-tags-collect\">");

		try {
			allTopicTags = TopicUtil.getAllTopicTags();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set allTopicTagsSet = allTopicTags.keySet();
//		List allTopicTagsList = new ArrayList(allTopicTagsSet);
//		Collections.sort(allTopicTagsList);
//		Iterator it = allTopicTagsList.iterator();
		Iterator it = allTopicTagsSet.iterator();
		List<TopicTag> showTopicTags = new ArrayList<TopicTag>();

		while(it.hasNext()){
			TopicTag topicTag = (TopicTag)allTopicTags.get( it.next() );
			if(tagsStyle.equals(TECHNOLOGY_ALL_TAGS)){
				if(topicTag.getParentId()==null || (topicTag.getParentId()!=2) ){
					showTopicTags.add(topicTag);
				}else
					continue;
			}else if(tagsStyle.equals(TECHNOLOGY_MAIN_TAGS)){
				if(topicTag.getParentId()!=null && topicTag.getParentId()==1){
					showTopicTags.add(topicTag);
				}else
					continue;				
			}else if(tagsStyle.equals(AUTHOR_ALL_TAGS)){
				if(topicTag.getParentId()!=null && topicTag.getParentId()==2){
					showTopicTags.add(topicTag);
				}else
					continue;
			}
		}
		sb.append("<a href=\"index.html\">"+"全部"+"</a>"+" ");
		Collections.sort(showTopicTags);
		Iterator showIt = showTopicTags.iterator();
		while(showIt.hasNext()){
			TopicTag topicTag = (TopicTag)showIt.next();
			sb.append("<a href=\"index-"+topicTag.getEnName()+".html\">"+topicTag.getName()+"</a>"+"("+topicTag.getTopicNum()+")");
			sb.append(" ");
		}
		sb.append("</span>"+allTopicHeader.size()+"</div>");
		pw.println(sb.toString());
		return sb.toString();
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
//			topicHeader.setPath(topicHeader.getPath().substring(7,topicHeader.getPath().length()));
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
					
					Set set = Pinyin4j.getPinyin(tag);
					Iterator setIt = set.iterator();
					String tagEnName = (String)setIt.next();
					
					tagsSB.append( tag  + " ");
					styleTagsSB.append( "<a class=\"techlib-a-topicTag\" href=\"index-"+tagEnName+".html\">"+tag+"</a>&nbsp;" );
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



			String titleTagA = "<a class=\"topicFirstTag\" href=\"index-"+topicHeader.getTags().get(0)+".html\">"+topicHeader.getTags().get(0)+"</a>";
			pw.println("<td class=\"row\">");
			pw.println(titleTagA);
			pw.println("</td>");

			pw.println("<td class=\"row\">");
			if( !topicHeader.getSubmitDate().equals(""))
				pw.println("<span class=\"techlib-little-gray\">"+topicHeader.getSubmitDate()+"</span>");
			else
				pw.println("<span class=\"techlib-little-gray\">"+"&nbsp;"+"</span>");
			
			String authorName = topicHeader.getAuthor();
			Set set = Pinyin4j.getPinyin(authorName);
			Iterator setIt = set.iterator();
			authorName = (String)setIt.next();
			
			pw.println( "<br/><span class=\"techlib-topic-list-author\"><a href=\"index-"+authorName+".html\">"+topicHeader.getAuthor()+"</a></span>" );
//			pw.println( "<br/><span class=\"techlib-topic-list-author\">"+topicHeader.getAuthor()+"</span>" );
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