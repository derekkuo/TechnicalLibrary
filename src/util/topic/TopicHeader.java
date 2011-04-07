package util.topic;

import java.io.Serializable;
import java.util.List;

public class TopicHeader implements Serializable{
	private String path;
	private String title;
	private String author;
	private List<String> tags;
	
	public TopicHeader(String path, String title, String author,
			List<String> tags) {
		super();
		this.path = path;
		this.title = title;
		this.author = author;
		this.tags = tags;
	}


	public List<String> getTags() {
		return tags;
	}


	public void setTags(List<String> tags) {
		this.tags = tags;
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
