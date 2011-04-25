package util.oa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class GenOAOrderPageTask extends TimerTask {
	private static boolean isRunning = false;
	private static String htmlTitle = "办公用品汇总";
	private static ServletContext context;
	private static String workspacePath;

	private static File file=null;
	private static PrintWriter pw =null;

	private Map<String,Order> orders = new HashMap<String,Order>();
	
	public GenOAOrderPageTask() {}
	public GenOAOrderPageTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			System.out.println(new Date()+" 生成oa/Order.html ........");
			workspacePath = context.getInitParameter( "workspacePath" );
			GenOAOrderPageTask gbpt = new GenOAOrderPageTask();

			parsexml(workspacePath+context.getContextPath()+"/WebContent/oa/order.xml");
			
			String orderIndexHtmlPath = workspacePath+context.getContextPath()+"/WebContent/oa/order.html";
			gbpt.writeOAOrderHtml(orderIndexHtmlPath, orders, context);
		}
	}
	
	public <E> void writeOAOrderHtml(String path, Map<String, Order> orders,  ServletContext context){

		try {
			file = new File( path );
			pw = new PrintWriter( file, "utf-8" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	
		String htmlHead = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" + "\n" +
		"<title>"+htmlTitle+"</title>"+ "\n";
		pw.write(htmlHead);
		
		InputStream is = context.getResourceAsStream( "/oa/oa-htmlhead.html" );
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		String strLine = null;
		try {
			br= new BufferedReader( new InputStreamReader( is,"utf-8") );
			while( (strLine = br.readLine()) != null){
				sb.append(strLine);
				sb.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pw.println(sb.toString());
		
		pw.println("<div id=\"techlib-head\"><h1>"+htmlTitle+"</h1></div>");
		pw.println("<div id=\"techlib-content\">");
		
		writeTableStyle(orders);
		
		pw.write("</div>");
		pw.write("</div>");
		pw.flush();
		pw.close();
	}

	public Map<String, Item> gatherOrdersProducts(Map<String, Order> orders){
		Map<String, Item> result = new HashMap<String, Item>();

		Set set = orders.keySet();
		Iterator it = set.iterator();
		List<String> keyList = new ArrayList<String>(set);
		Collections.sort(keyList);
		while(it.hasNext()){
			Order order = (Order)orders.get( it.next() );
			List<Item> itemList = order.getItems();
			Iterator itItemList = itemList.iterator();
			while(itItemList.hasNext()){
				Item item = (Item)itItemList.next();
				String key = item.getProductName();
				if( result.containsKey( key ) ){
					Item containItem = result.get(key);
					Item newItem = new Item(
							containItem.getProductName(),
							containItem.getProductType(),
							containItem.getAmount()+item.getAmount());

					result.put(key, newItem);
				}else{
					result.put(key, item);
				}
			}
		}
		
		return result;
	}
	
	public void writeTableStyle( Map<String, Order> orders ){
		Set set = orders.keySet();
		List<String> keylist = new ArrayList<String>(set);
		Collections.sort(keylist);
		
		
		
		/*****************************汇总******************/
		Map<String, Item> result = gatherOrdersProducts(orders);
		pw.println("<h2>汇总</h2>");
		pw.println("<table id=\"gathertable\" cellspacing=\"0\">");

		pw.println("<thead><tr>");
		pw.println("<th scope=\"col\">序号</th>" +
				"<th scope=\"col\">物品名称</th>" +
				"<th scope=\"col\">规格及型号</th>" +
				"<th scope=\"col\">数量</th>");
		pw.println("</tr></thead><tbody>");
		
		Set resultKeySet = result.keySet();
		List<String> resultkeyList = new ArrayList<String>(resultKeySet);
		Collections.sort(resultkeyList);
		Iterator<String> resultIt = resultkeyList.iterator();
		int resultNumber = 1;
		while(resultIt.hasNext()){
			Item item = result.get(resultIt.next());
			pw.println("<tr>");

			pw.println("<td class=\"row\">");
			pw.println( resultNumber++ );
			pw.println("</td>");

			pw.println("<td class=\"row\">");
			pw.println( item.getProductName() );
			pw.println("</td>");

			pw.println("<td class=\"row\">");
			if("".equals( item.getProductType() ))
				pw.println( "&nbsp;" );
			else
				pw.println( item.getProductType() );
			pw.println("</td>");

			pw.println("<td class=\"row\">");
			pw.println( item.getAmount() );
			pw.println("</td>");
			
			pw.println("</tr>");
		}
		
		
		pw.println("</tbody></table>");

		
		/*******************办公用品详情*********************/
		pw.println("<h2>详情</h2>");
		pw.println("<table id=\"mytable\" cellspacing=\"0\">");

		pw.println("<thead><tr>");
		pw.println("<th scope=\"col\">序号</th>" +
				"<th scope=\"col\">物品名称</th>" +
				"<th scope=\"col\">规格及型号</th>" +
				"<th scope=\"col\">数量</th>" +
				"<th scope=\"col\">姓名</th>" +
				"<th scope=\"col\">备注</th>");
		pw.println("</tr></thead><tbody>");
		
		int number = 1;
		for(int i=0; i<keylist.size(); i++){
			Order order = orders.get(keylist.get(i));
			if("".equals(order.getId()))//如果没有员工编号，则不算
				continue;
			
			
			List<Item> items = order.getItems();
			Iterator it = items.iterator();
			while(it.hasNext()){
				
				
				pw.println("<tr>");

				pw.println("<td class=\"row\">");
				pw.println( number++ );
				pw.println("</td>");

				
				
				
				
				Item item = (Item)it.next();
				pw.println("<td class=\"row\">");
				pw.println( item.getProductName() );
				pw.println("</td>");
				
				pw.println("<td class=\"row\">");
				if("".equals( item.getProductType() ))
					pw.println( "&nbsp;" );
				else
					pw.println( item.getProductType() );
				pw.println("</td>");				

				pw.println("<td class=\"row\">");
				pw.println( item.getAmount() );
				pw.println("</td>");			

			
			
				pw.println("<td class=\"row\">");
				pw.println( order.getName() );
				pw.println("</td>");
				
				pw.println("<td class=\"row\">");
				if("".equals(order.getSummary()))
					pw.println("&nbsp;");
				else
					pw.println( order.getSummary() );
				pw.println("</td>");
			
				pw.println("</tr>");
			
			}
			

		}
		pw.println("</tbody></table>");
	}

	public void parsexml(String filePath) {

		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File( filePath ));
			List list = doc.selectNodes("/orders/order");
			for (int i = 0; i < list.size(); i++) {
				Element e1 = (Element) list.get(i);
				Element id = (Element) e1.selectSingleNode("id");
				Element name = (Element) e1.selectSingleNode("name");
				Element summary = (Element) e1.selectSingleNode("summary");

				Element itemsElement = (Element) e1.selectSingleNode("items");
				List<Element> itemElements = itemsElement.elements();

				List<Item> items = new ArrayList<Item>();
				for(int j=0; j<itemElements.size(); j++){
					Element el = (Element) itemElements.get(j);
					String productName = el.selectSingleNode("product-name").getText().trim();
					String productType = el.selectSingleNode("product-type").getText().trim();
					String amount = el.selectSingleNode("amount").getText().trim();
//					System.out.println(productName+" "+productType+" "+amount);
					items.add( new Item(productName, productType, Integer.parseInt(amount)) );
				}
				
				Order order = new Order(id.getText().trim(),
						name.getText().trim(),
						summary.getText().trim(),
						items);
				

				orders.put(id.getText().trim(),order);
//				System.out.println(orders);
				
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}