package util;

public class TopicHeader {
	private String path;
	private String title;
	private String author;
	
	public TopicHeader(String path, String title, String author) {
		super();
		this.path = path;
		this.title = title;
		this.author = author;
	}
	
	
	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
