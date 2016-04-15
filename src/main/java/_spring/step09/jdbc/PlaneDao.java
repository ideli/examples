package _spring.step09.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

public class PlaneDao {

	@Autowired
	private DataFieldMaxValueIncrementer incr;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public void createTable() {
		String sql = "create table t_plane(plane_id int not null,plane_name varchar(50),PRIMARY KEY(plane_id))";
		jdbcTemplate.execute(sql);
	}
	
	public void createIdTable() {
		String sql = "create table t_plane_id(seq_id int) type=MYISAM;insert into t_plane_id values(0);";
		jdbcTemplate.execute(sql);
	}
	
	public void addPlane(Plane plane) {
		String sql = "INSERT INTO t_plane(plane_id,plane_name) VALUES(:id,:name)";
		MapSqlParameterSource msps = new MapSqlParameterSource().addValue("id", incr.nextIntValue())
				.addValue("name", plane.getName());
		namedParameterJdbcTemplate.update(sql, msps);
		
	}
}
