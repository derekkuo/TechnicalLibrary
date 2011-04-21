package util.oa;

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

import util.bookmark.Bookmark;
import util.topic.TopicHeader;

public class GenOAEmployeePageTask extends TimerTask {
	private static boolean isRunning = false;
	private ServletContext context;
	private String workspacePath;

	File file=null;
	PrintWriter pw =null;

	private Map<String,Employee> employees = new HashMap<String,Employee>();
	
	public GenOAEmployeePageTask() {}
	public GenOAEmployeePageTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			System.out.println(new Date()+" 生成oa/employee.html ........");
			workspacePath = context.getInitParameter( "workspacePath" );
			GenOAEmployeePageTask gbpt = new GenOAEmployeePageTask();

			parsexml(workspacePath+context.getContextPath()+"/WebContent/oa/employee.xml");
			
			String bookmarkIndexHtmlPath = workspacePath+context.getContextPath()+"/WebContent/oa/employee.html";
			gbpt.writeOAEmployeeIndexHtml(bookmarkIndexHtmlPath, employees);
		}
	}
	
	public <E> void writeOAEmployeeIndexHtml(String path, Map<String, Employee> employees){

		try {
			file = new File( path );
			pw = new PrintWriter( file, "utf-8" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		String htmlHead = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" + "\n" +
		"<title>Employee - OA System</title>"+ "\n" +
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topic-index.css\"></link>"+ "\n" +
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/techlib-topbar.css\"></link>"+ "\n" +
		"<script type=\"text/javascript\" src=\"../js/jquery-1.4.3.min.js\"></script>"+ "\n" +
		"<script type=\"text/javascript\" src=\"../js/jquery.tablesorter.js\"></script>"+ "\n" +
		"<script type=\"text/javascript\" src=\"../js/techlib-topic-index.js\"></script>"+ "\n" +
		"</head>"+ "\n" +
		"<div id=\"techlib-body\">"+ "\n" +
		"<div id=\"topbar\"><strong>技术资料库 Technical Library</strong>&nbsp;&nbsp;<a href=\"../index.html\">首页|Home</a>&nbsp;&nbsp;<a href=\"../topic/index.html\">文章|Topic</a>&nbsp;&nbsp;<a href=\"../bookmark/index.html\">书签|Bookmark</a></div>"+ "\n" +
		"<div id=\"techlib-head\"><h1>通讯录</h1></div>"+ "\n" +
		"<div id=\"techlib-content\">";
		pw.write(htmlHead);
		
		writeTableStyle(employees);
		
		pw.write("</div></div>");
		pw.flush();
		pw.close();
	}

	public void writeTableStyle( Map<String, Employee> employees ){
		Set set = employees.keySet();
		List<String> keylist = new ArrayList<String>(set);
		Collections.sort(keylist);
		pw.println("<table id=\"mytable\" cellspacing=\"0\">");

		pw.println("<thead><tr>");
		pw.println("<th scope=\"col\">序号</th><th scope=\"col\">编号</th><th scope=\"col\">姓名</th><th scope=\"col\">电话</th><th scope=\"col\">电子邮箱</th><th scope=\"col\">qq/msn</th><th scope=\"col\">说明</th>");
		pw.println("</tr></thead><tbody>");
		
		for(int i=0; i<keylist.size(); i++){
			Employee employee = employees.get(keylist.get(i));
			if("".equals(employee.getId()))
				continue;
			
			pw.println("<tr>");

			pw.println("<td class=\"row\">");
			pw.println(i+1);
			pw.println("</td>");

			
			pw.println("<td class=\"row\">");
			pw.println(employee.getId());
			pw.println("</td>");

			pw.println("<td class=\"row\">");
			pw.println(employee.getName());
			pw.println("</td>");
			
			pw.println("<td class=\"row\">");
			pw.println(employee.getPhone());
			pw.println("</td>");



			pw.println("<td class=\"row\">");
			pw.println("<a title=\"给 "+employee.getName()+" 发邮件\" href=\"mailto:"+employee.getEmail()+"?Subject=Hello\">");
			pw.println(employee.getEmail());
			pw.println("</a></td>");
			

			pw.println("<td class=\"row\">");
			if( employee.getQqMsn().indexOf("@") == -1)
				pw.println( "<a alt=\"点击这里给 "+employee.getName()+" 发QQ消息\" title=\"点击这里给 "+employee.getName()+" 发QQ消息\" target=\"_blank\" href=\"http://wpa.qq.com/msgrd?v=3&uin="+employee.getQqMsn()+"&site=qq&menu=yes\"><img border=\"0\" src=\"http://wpa.qq.com/pa?p=2:"+employee.getQqMsn()+":45\"> "+employee.getQqMsn()+"</a>" );
			else
				pw.println( employee.getQqMsn() );
			pw.println("</td>");
			
			
			pw.println("<td class=\"row\">");
			if("".equals(employee.getSummary()))
				pw.println( "&nbsp;");
			else
				pw.println( employee.getSummary());
			pw.println("</td>");
		
			pw.println("</tr>");
		}
		pw.println("</tbody></table>");
	}

	public void parsexml(String filePath) {

		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File( filePath ));
			List list = doc.selectNodes("/employees/employee");
			for (int i = 0; i < list.size(); i++) {
				Element e1 = (Element) list.get(i);
				Element id = (Element) e1.selectSingleNode("id");
				Element name = (Element) e1.selectSingleNode("name");
				Element phone = (Element) e1.selectSingleNode("phone");
				Element qqMsn = (Element) e1.selectSingleNode("qq-msn");
				Element email = (Element) e1.selectSingleNode("email");
				Element summary = (Element) e1.selectSingleNode("summary");

				Employee employee = new Employee(
						id.getText().trim(),
						name.getText().trim(),
						phone.getText().trim(),
						qqMsn.getText().trim(),
						email.getText().trim(),
						summary.getText().trim()
				);
				

				employees.put(id.getText().trim(),employee);
//				System.out.println(employees);
				
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}