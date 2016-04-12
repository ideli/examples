package _spring.step08.transaction.annotation;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void addUser(int userId, String userName) {
		String sql = "insert into tb_user(UserId,UserName) values(?, ?)";
		jdbcTemplate.update(sql, new Object[] { userId, userName }, new int[] { Types.INTEGER, Types.VARCHAR });
	}

	public void updateUser(int userId, String userName) {
		String sql = "update tb_user set UserName=? where UserId=?";
		jdbcTemplate.update(sql, new Object[] { userName, userId }, new int[] { Types.VARCHAR, Types.INTEGER });
	}

	public String getUserName(int userId) {
		String sql = "select UserName from tb_user where UserId=?";
		return jdbcTemplate.queryForObject(sql, String.class, new Object[] { userId }, new int[] { Types.INTEGER });
	}

	public int getMaxUserId() {
		String sql = "select MAX(UserId)+1 as MaxUserId from tb_user";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
