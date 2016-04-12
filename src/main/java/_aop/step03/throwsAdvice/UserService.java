package _aop.step03.throwsAdvice;

import java.sql.SQLException;

/**
 * 异常抛出增强 - 目标类
 */
public class UserService {

	public void removeUser(int userId) {
		//do sth...
		throw new RuntimeException("运行异常");
	}
	
	public void updateUser(int userId, String userName) throws SQLException {
		//do sth...
		throw new SQLException("更新失败");
	}
}
