package _database.helper;

import java.io.Serializable;

public class DataColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2479233804228856282L;
	
	String key;
	Object value;
	
	public DataColumn(String _key,Object _value) {
		key = _key;
        value = _value;
    }
	
	public String getKey() {
        return key;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void SetKey(String _key) {
        key = _key;
    }
    
    public void SetValue(Object _value) {
        value = _value;
    }
}
