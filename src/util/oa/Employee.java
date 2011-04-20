package util.oa;

public class Employee {
	private String id;
	private String name;
	private String phone;
	private String qqMsn;
	private String email;
	private String summary;
	public Employee(String id, String name, String phone, String qqMsn,
			String email, String summary) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.qqMsn = qqMsn;
		this.email = email;
		this.summary = summary;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQqMsn() {
		return qqMsn;
	}
	public void setQqMsn(String qqMsn) {
		this.qqMsn = qqMsn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
