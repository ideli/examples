package _json.fastjson;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Client {

	public static void main(String[] args) {
		new Client().toJSONObject();
		new Client().toObject();
		new Client().mapToJson();
	}

	public void toJSONObject() {
		String json = "{\"id\":20130001,\"phone\":\"13579246810\",\"name\":\"Jason\"}";
		JSONObject jo = JSON.parseObject(json);
		System.out.println("jo.get(\"not exists key\")=" + jo.get("not exists key"));
		System.out.println("jo.getOrDefault(\"not exists key\")=" + jo.getOrDefault("not exists key", ""));
		System.out.println("jo.get(\"id\")=" + jo.get("id"));
	}
	
	public void toObject() {
		String json = "{\"id\":20130001,\"phone\":\"13579246810\",\"name\":\"Jason\"}";
		Card card = JSON.parseObject(json, Card.class);
		System.out.println(card);
		System.out.println(JSON.toJSONString(card));
	}
	
	public void mapToJson() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", 20130001);
		map.put("phone", "13579246810");
		map.put("name", "Jason");
		System.out.println(JSON.toJSONString(map));
	}
}
