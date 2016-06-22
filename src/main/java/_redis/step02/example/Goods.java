package _redis.step02.example;

import java.util.Set;

import com.alibaba.fastjson.JSON;

public class Goods {

	public Goods() {}
	
	public Goods(long id, String title, float price) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
	}
	
	private long id;
	private String title;
	private float price;
	private Set<String> tags;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Set<String> getTags() {
		return tags;
	}
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
	
	public static Goods parseJson(String json) {
		return JSON.parseObject(json, Goods.class);
	}
}
