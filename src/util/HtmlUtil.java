package util;

import java.io.IOException;
import java.io.InputStream;

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
}
