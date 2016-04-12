package _database.pool;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DbcpConnectionManager {

    private static BasicDataSource dataSource;
	public Connection getConnection() throws SQLException {
		if(dataSource == null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("_database/pool/spring-dbcp.xml");
			dataSource = (BasicDataSource)ctx.getBean("dbcpDataSource");
		}
		return dataSource.getConnection();
	}
	
	public static void CloseConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Connection conn = new DbcpConnectionManager().getConnection();
		conn.close();
	}
}
