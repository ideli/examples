package _database.helper;

import java.io.Serializable;
import java.util.List;

public class DataTable implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9028013823449863822L;
	
	List<DataRow> row;
    public DataTable(){}
    public DataTable(List<DataRow> _row) {
    	this.row = _row;
    }
    
    public List<DataRow> getRows() {  
        return row; 
    }
    
    public void setRow(List<DataRow> _row) {  
        row = _row;
    }
    
    public static void PrintTable(DataTable dt) {
        for (DataRow r : dt.getRows()) {
            for (DataColumn c : r.getColumns()) {
                System.out.print(c.getKey() + ":" + c.getValue() + "  ");
            }
            System.out.println("");
        }
    }
    
    private int iRowCount;
    
    public int getRowCount() {
    	return this.iRowCount;
    }
    
    public void setRowCount(int iRowCount) {
    	this.iRowCount = iRowCount;
    }
    
    private int iColumnCount;
    
    public int getColumnCount() {
    	return this.iColumnCount;
    }
    
    public void setColumnCount(int iColumnCount) {
    	this.iColumnCount = iColumnCount;
    }
}