package _database.helper;

import java.io.Serializable;
import java.util.Date;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class DataRow implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8003889893386973752L;
	
	List<DataColumn> col;
    public DataRow(List<DataColumn> _col) {
        col = _col;
    }
    
    public List<DataColumn> getColumns() {
        return col;
    }
      
    public void setColumns(List<DataColumn> _col) {
        col = _col;
    }
    
    public DataColumn getColumn(String colName) {
        for(DataColumn c:col) {
            if(c.getKey().toUpperCase().equals(colName.toUpperCase())) {
            	return c;
            }
        }
        
        return null;
    }
    
    public int getColumnInt(String colName) {
        for(DataColumn c:col) {
            if(c.getKey().toUpperCase().equals(colName.toUpperCase())) {
            	if(c.getValue() == null || c.getValue().equals(""))
            		return 0;
            	else
            		try {
            			return Integer.parseInt(c.getValue().toString());
            		} catch (NumberFormatException e) {
            			return 0;
            		}
            }
        }
        return 0;
    }
    
    public String getColumnString(String colName) {
        for(DataColumn c:col) {
            if(c.getKey().toUpperCase().equals(colName.toUpperCase())) {
            	if(c.getValue() == null)
            		return "";
            	else
            		return c.getValue().toString();
            }
        }
        return "";
    }
    
    public Date getColumnDate(String colName) {
        for(DataColumn c:col) {
            if(c.getKey().toUpperCase().equals(colName.toUpperCase())) {
            	if(c.getValue() == null)
            		return null;
            	else {
            		String strDateValue = c.getValue().toString();
            		if(strDateValue.indexOf(".") > -1) {
            			strDateValue = strDateValue.substring(0, strDateValue.indexOf("."));
            		}
            		try {
						return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDateValue);
					} catch (ParseException e) {
						e.printStackTrace();
					}
            	}
            }
        }
        return null;
    }
    
    public Blob getColumnBlob(String colName) {
        for(DataColumn c:col) {
            if(c.getKey().toUpperCase().equals(colName.toUpperCase())) {
            	return (Blob)c.getValue();
            }
        }
        return null;
    }
    
    public float getColumnFloat(String colName) {
        for(DataColumn c:col) {
            if(c.getKey().toUpperCase().equals(colName.toUpperCase())) {
            	if(c.getValue() == null || c.getValue().equals(""))
            		return 0;
            	else
            		try {
            			return Float.parseFloat(c.getValue().toString());
            		} catch (NumberFormatException e) {
            			return 0;
            		}
            }
        }
        return 0;
    }
}
