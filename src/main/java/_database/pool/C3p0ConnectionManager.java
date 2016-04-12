package _database.pool;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0ConnectionManager {

	protected static ComboPooledDataSource dataSource = null;
	
	public Connection getConnection() throws SQLException {
		if(dataSource == null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("_database/pool/spring-c3p0.xml");
			dataSource = (ComboPooledDataSource)ctx.getBean("c3p0DataSource");
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
		Connection conn = new C3p0ConnectionManager().getConnection();
		conn.close();
	}
}
