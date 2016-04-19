package _spring.step09.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class CarDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	final String INSERT_SQL = "insert into t_car(car_name,car_price) values(?, ?)";
	final String SELECT_SQL = "select * from t_car where car_id=?";
	
	public void createTable() {
		String sql = "create table t_car(car_id int not null AUTO_INCREMENT,car_name varchar(50),car_price float,PRIMARY KEY(car_id)) default charset=utf8";
		jdbcTemplate.execute(sql);
	}
	
	public void dropTable() {
		String sql = "drop table t_car";
		jdbcTemplate.execute(sql);
	}
	
	//注意: 尽量显示指定每个占位符对应的字段数据类型
	public void addCar(Car car) {
		jdbcTemplate.update(INSERT_SQL, new Object[] { car.getName(), car.getPrice() }, new int[] { Types.VARCHAR, Types.FLOAT });
	}
	
	//获得主键
	public int addCarAndGetKey(Car car) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
				ps.setString(1, car.getName());
				ps.setFloat(2, car.getPrice());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public void batchAddCar() {
		Car[] cars = new Car[] {
			new Car(1, "Audi A4", 300000f),
			new Car(2, "Benz C200", 300000f),
			new Car(3, "BMW 323", 300000f)
		};
		jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return cars.length;
			}

			@Override
			public void setValues(PreparedStatement ps, int index)
					throws SQLException {
				ps.setString(1, cars[index].getName());
				ps.setFloat(2, cars[index].getPrice());
			}
		});
	}
	
	//使用RowCallbackHandler
	public Car getCarById(final int carId) {
		final Car car = new Car();
		jdbcTemplate.query(SELECT_SQL, new Object[]{ carId }, new int[]{ Types.INTEGER }, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				car.setId(carId);
				car.setName(rs.getString("car_name"));
				car.setPrice(rs.getFloat("car_price"));
			}
		});
		return car;
	}
	
	public List<Car> getCars(final int fromId, final int toId) {
		String sql = "select * from t_car where car_id between ? and ?";
		final List<Car> cars = new ArrayList<>();
		jdbcTemplate.query(sql, new Object[]{ fromId, toId }, new int[]{ Types.INTEGER, Types.INTEGER }, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Car car = new Car();
				car.setId(rs.getInt("car_id"));
				car.setName(rs.getString("car_name"));
				car.setPrice(rs.getFloat("car_price"));
				cars.add(car);
			}
		});
		return cars;
	}
	
	//使用RowMapper(不能控制List大小, 如果返回结果集很大, 会过多消耗内存)
	public List<Car> getCars(final float fromPrice) {
		String sql = "select * from t_car where car_price>?";
		return jdbcTemplate.query(sql, new Object[]{ fromPrice }, new int[]{ Types.FLOAT }, new RowMapper<Car>() {

			@Override
			public Car mapRow(ResultSet rs, int index) throws SQLException {
				Car car = new Car();
				car.setId(rs.getInt("car_id"));
				car.setName(rs.getString("car_name"));
				car.setPrice(rs.getFloat("car_price"));
				return car;
			}
		});
	}
	
	//会一次读取所有数据到内存
	public SqlRowSet queryForRowSet(final float fromPrice) {
		String sql = "select * from t_car where car_price>?";
		return jdbcTemplate.queryForRowSet(sql, new Object[]{ fromPrice }, new int[]{ Types.FLOAT });
	}
	
	//调用存储过程
	public List<Car> findCars(int carId, String carName, float price) {
		String sql = "{call proc_FindCar(?,?,?)}";
		return jdbcTemplate.execute(sql, new CallableStatementCallback<List<Car>>() {

			@Override
			public List<Car> doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.setInt(1, carId);
				cs.setString(2, carName);
				cs.setFloat(3, price);
				cs.execute();
				List<Car> cars = new ArrayList<>();
				ResultSet rs = cs.getResultSet();
				while(rs.next()) {
					Car car = new Car();
					car.setId(rs.getInt("car_id"));
					car.setName(rs.getString("car_name"));
					car.setPrice(rs.getFloat("car_price"));
					cars.add(car);
				}
				return cars;
			}
			
		});
	}
	
	//读取BLOB/CLOB - LobHandler
	//略 参考<Spring 3.x企业应用开发实战> 377页
}
