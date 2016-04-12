package _database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import _database.helper.DBHelper;
import _database.helper.DataRow;
import _database.helper.DataTable;
import _database.helper.SqlParam;
import _database.helper.DBHelper.CommandType;
import _database.pool.C3p0ConnectionManager;


public class Demo {

	public void executeReturnAndOutputProc() {
		Connection conn = null;
		try {
			conn = new C3p0ConnectionManager().getConnection();
			//Java不支持按参数名匹配, 顺序必须还存储过程参数数序保持一致. Return型参数只能有一个, 且必须放在第一个
			SqlParam[] inParams = new SqlParam[] {
				SqlParam.makeReturnSqlParam(java.sql.Types.INTEGER),
				SqlParam.makeInSqlParam(2, java.sql.Types.INTEGER, 123),
				SqlParam.makeInSqlParam(3, java.sql.Types.VARCHAR, null, 30),
				SqlParam.makeInSqlParam(4, java.sql.Types.TIMESTAMP, "2015-05-21 12:00:00"),
				SqlParam.makeOutSqlParam(5, java.sql.Types.INTEGER),
				SqlParam.makeOutSqlParam(6, java.sql.Types.VARCHAR, 30),
				SqlParam.makeOutSqlParam(7, java.sql.Types.TIMESTAMP)
			};
			System.out.println("Call sp_OutputAndReturn");
			DBHelper.executeNonQuery(conn, CommandType.Procedure, "sp_OutputAndReturn", inParams);
			int iReturn = (int)inParams[0].getValue();
			int iParam1 = (int)inParams[4].getValue();
			String strParam2 = (String)inParams[5].getValue();
			Timestamp ts = (Timestamp)inParams[6].getValue();
			
			System.out.println("return " + iReturn);
			System.out.println("output1 " + iParam1);
			System.out.println("output2 " + strParam2);
			System.out.println("output3 " + ts);
			System.out.println("--------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3p0ConnectionManager.CloseConnection(conn);
		}
	}
	
	public void executeSelectSQL() {
		Connection conn = null;
		try {
			conn = new C3p0ConnectionManager().getConnection();
			SqlParam[] inParams = new SqlParam[] {
				SqlParam.makeInSqlParam(1, java.sql.Types.INTEGER, 1)
			};
			System.out.println("Call select * from App where AppId=?");
			List<DataTable> tables = DBHelper.executeDataSet(conn, CommandType.Text, "select * from App where AppId=?", inParams, true);
			if(tables != null && tables.size() > 0) {
				DataTable dt = tables.get(0);
				if(dt.getRowCount() > 0) {
					List<DataRow> rows = dt.getRows();
					DataRow row = rows.get(0);
					System.out.println(row.getColumnString("AppName"));
				}
			}
			System.out.println("--------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3p0ConnectionManager.CloseConnection(conn);
		}
	}
	
	public void executeUpdateSQL() {
		Connection conn = null;
		try {
			conn = new C3p0ConnectionManager().getConnection();
			SqlParam[] inParams = new SqlParam[] {
				SqlParam.makeInSqlParam(1, java.sql.Types.INTEGER, 1)
			};
			System.out.println("Call update App set Status=100 where AppId=?");
			DBHelper.executeNonQuery(conn, CommandType.Text, "update App set Status=100 where AppId=?", inParams);
			System.out.println("--------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3p0ConnectionManager.CloseConnection(conn);
		}
	}
	
	public void executeOutputAndSelectProc() {
		Connection conn = null;
		try {
			conn = new C3p0ConnectionManager().getConnection();
			SqlParam[] inParams = new SqlParam[] {
				SqlParam.makeInSqlParam(1, java.sql.Types.INTEGER, 123),
				SqlParam.makeOutSqlParam(2, java.sql.Types.INTEGER),
			};
			
			System.out.println("Call sp_OutputAndSelect");
			DataTable dt = DBHelper.executeDataTable(conn, CommandType.Procedure, "sp_OutputAndSelect", inParams);
			int iParam1 = (int)inParams[1].getValue();
			
			System.out.println("output1 " + iParam1);
			System.out.println("Rows: " + dt.getRowCount());
			if(dt.getRowCount() > 0) {
				List<DataRow> rows = dt.getRows();
				DataRow row = rows.get(0);
				System.out.println(row.getColumnString("AppName"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3p0ConnectionManager.CloseConnection(conn);
		}
	}
}
