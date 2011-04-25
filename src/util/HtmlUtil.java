package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HtmlUtil {
	
	public static Document getJsoupDocument(InputStream inputStream){
		Document document = null;
		try {
			document = Jsoup.parse( inputStream, "UTF-8", "http://localhost:8080/TechnicalLibrary/" );
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件不存在");
		}
		return document;
	}

	public static void printHtmlFromInputStream( PrintWriter pw, InputStream is){

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
	}
}
