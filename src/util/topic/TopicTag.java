package util.topic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.message.RelatesToHeader;

public class TopicTag implements Serializable,Comparable{
	private Long id;
	
	private String enName;
	private String name;
	private Integer turn;
	private String description;
	
	private Integer parentId;

	private int topicNum;

	private List<TopicHeader> topicHeaders = new ArrayList<TopicHeader>();
	private List<TopicTag> relationTopicTags = new ArrayList<TopicTag>();	

	@Override
	public int compareTo(Object o) {
		TopicTag otherTag = (TopicTag)o; 
		if( otherTag.getTopicNum() - this.getTopicNum() == 0 )
			return -1*otherTag.getName().compareTo( this.getName() );
		else
			return otherTag.getTopicNum() - this.getTopicNum(); 
	}
	
	public TopicTag() {
	}

	public TopicTag(String name){
		this.name = name;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enName == null) ? 0 : enName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TopicTag other = (TopicTag) obj;
		if (enName == null) {
			if (other.enName != null)
				return false;
		} else if (!enName.equals(other.enName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	public List<TopicTag> getRelationTopicTags() {
		return relationTopicTags;
	}

	public void setRelationTopicTags(List<TopicTag> relationTopicTags) {
		this.relationTopicTags = relationTopicTags;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTurn() {
		return turn;
	}

	public void setTurn(Integer turn) {
		this.turn = turn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public int getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}

	public List<TopicHeader> getTopicHeaders() {
		return topicHeaders;
	}

	public void setTopicHeaders(List<TopicHeader> topicHeaders) {
		this.topicHeaders = topicHeaders;
	}
	
	
}
