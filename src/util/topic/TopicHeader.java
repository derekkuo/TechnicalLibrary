package util.topic;

import java.io.Serializable;
import java.util.List;

import org.jsoup.select.Elements;

public class TopicHeader implements Serializable,Comparable {
	private String path;
	private String title;
	private String subtitle;
	private String author;
	private List<String> tags;
	private String summary;
	private Elements menu;
	private String submitDate;
	
	public TopicHeader(String path, String title, String subtitle,
			String author, List<String> tags, String summary, Elements menu, String submitDate) {
		super();
		this.path = path;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.tags = tags;
		this.summary = summary;
		this.menu = menu;
		this.submitDate = submitDate;
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
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Elements getMenu() {
		return menu;
	}
	public void setMenu(Elements menu) {
		this.menu = menu;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	@Override
	public String toString() {
		return "TopicHeader [path=" + path + ", title=" + title + ", subtitle="
				+ subtitle + ", author=" + author + ", tags=" + tags
				+ ", summary=" + summary + ", menu=" + menu + ", submitDate="
				+ submitDate + "]";
	}

	@Override
	public int compareTo(Object o) {
		return ((TopicHeader)o).getSubmitDate().compareToIgnoreCase( submitDate );
	}
	
	
	
	
}
