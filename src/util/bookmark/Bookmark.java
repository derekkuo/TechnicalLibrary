package util.bookmark;

import java.util.List;

public class Bookmark {
	private long id;
	private String url;
	private String title;
	private String ResourceType;//文章 电子书 软件 
	private String TechnicalType;
	private List<String> tags;
	private String provider;
	private String summary;
	
	
	
	public Bookmark(long id, String url, String title, String ResourceType, String summary) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.ResourceType = ResourceType;
		this.summary = summary;
	}
	public Bookmark(long id, String url, String title, String resourceType,
			String technicalType, List<String> tags, String provider,
			String summary) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		ResourceType = resourceType;
		TechnicalType = technicalType;
		this.tags = tags;
		this.provider = provider;
		this.summary = summary;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getResourceType() {
		return ResourceType;
	}
	public void setResourceType(String resourceType) {
		ResourceType = resourceType;
	}
	public String getTechnicalType() {
		return TechnicalType;
	}
	public void setTechnicalType(String technicalType) {
		TechnicalType = technicalType;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ResourceType == null) ? 0 : ResourceType.hashCode());
		result = prime * result
				+ ((TechnicalType == null) ? 0 : TechnicalType.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bookmark other = (Bookmark) obj;
		if (ResourceType == null) {
			if (other.ResourceType != null)
				return false;
		} else if (!ResourceType.equals(other.ResourceType))
			return false;
		if (TechnicalType == null) {
			if (other.TechnicalType != null)
				return false;
		} else if (!TechnicalType.equals(other.TechnicalType))
			return false;
		if (id != other.id)
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Bookmark [id=" + id + ", url=" + url + ", title=" + title
				+ ", ResourceType=" + ResourceType + ", TechnicalType="
				+ TechnicalType + ", tags=" + tags + ", provider=" + provider
				+ ", summary=" + summary + "]";
	}

	

}
