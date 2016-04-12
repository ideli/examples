package _json;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class LiteJsonParser {

	public void parseJson() {
		String strJson = "{\"id\":20130001,\"phone\":\"13579246810\",\"name\":\"Jason\"}";
		try {
			JSONObject jsonObject = new JSONObject(strJson);
			
			int iId = jsonObject.getInt("id");
			System.out.println("id=" + iId);
			String strPhone = jsonObject.getString("phone");
			System.out.println("phone=" + strPhone);
			
			if(jsonObject.has("name")) {
				String strName = jsonObject.getString("name");
				System.out.println("name=" + strName);
			} else {
				System.out.println("Key name not exists");
			}
			if(jsonObject.has("sex")) {
				String strSex = jsonObject.getString("sex");
				System.out.println("sex=" + strSex);
			} else {
				System.out.println("Key sex not exists");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void createJson() {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", "Jason");
			jsonObject.put("id", 20130001);
			jsonObject.put("phone", "13579246810");
			System.out.println(jsonObject.toString());
			
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object();  
            jsonStringer.key("name");  
            jsonStringer.value("Jason");  
            jsonStringer.key("id");  
            jsonStringer.value(20130001);  
            jsonStringer.key("phone");  
            jsonStringer.value("13579246810");  
            jsonStringer.endObject();
            System.out.println(jsonStringer.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
