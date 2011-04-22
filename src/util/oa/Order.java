package util.oa;

import java.util.List;

public class Order {
	private String id;
	private String name;
	private String summary;
	private List<Item> items;
	public Order(String id, String name, String summary, List<Item> items) {
		super();
		this.id = id;
		this.name = name;
		this.summary = summary;
		this.items = items;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}

class Item{
	private String productName;
	private String productType;
	private int amount;
	public Item(String productName, String productType, int amount) {
		super();
		this.productName = productName;
		this.productType = productType;
		this.amount = amount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}