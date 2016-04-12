package _spring.step08.transaction.annotation;

import java.sql.SQLException;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 属性说明
 * value: 事务管理器名称, 如果只有一个事务管理器, 直接写成@Transactional即可
 * propagation: 事务传播行为, 如propagation=Propagation.REQUIRED
 *   REQUIRED 如果当前没有事务, 就新建一个事务, 如果已经存在一个事务中, 加入到这个事务中(最常见的选择)
 *   SUPPORTS 支持当前事务, 如果当前没有事务, 就以非事务方式执行
 *   MANDATORY 使用当前的事务, 如果当前没有事务, 就抛出异常
 *   REQUIRES_NEW 新建事务, 如果当前存在事务, 把当前事务挂起
 *   NOT_SUPPORTED 以非事务方式执行操作, 如果当前存在事务,就把当前事务挂起
 *   NEVER 以非事务方式执行, 如果当前存在事务, 则抛出异常
 *   NESTED 如果当前存在事务, 则在嵌套事务内执行; 如果当前没有事务, 则执行与PROPAGATION_REQUIRED类 似的操作
 * isolation: 事务隔离级别, 如isolation=Isolation.DEFAULT
 * readOnly: 事务读写性(如果只读不能修改任何数据)
 * rollbackFor: 遇到哪些类型异常回滚, 如rollbackFor={SQLException.class,...}
 * rollbackForClassName: 遇到哪些类型异常回滚, 如rollbackForClassName={"SQLException",...}
 * noRollbackFor: 遇到哪些类型异常不回滚, 如noRollbackFor={SQLException.class,...}
 * noRollbackForClassName: 遇到哪些类型异常不回滚, 如noRollbackForClassName={"SQLException",...}
 */
@Transactional("user")
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//覆盖类级的注解
	@Transactional(value="user",readOnly=true)
	@Override
	public String getUserName(int userId) {
		return this.userDao.getUserName(userId);
	}

	@Override
	public void addUser(String userName) {
		int userId = this.userDao.getMaxUserId();
		this.userDao.addUser(userId, userName);
		//如果演示异常回滚, 则插入重复的userId, 触发重复键错误
		boolean makeError = true;
		if(!makeError) {
			userId++;
		}
		this.userDao.addUser(userId, userName + "_copy");
	}

	@Override
	public void updateUser(int userId, String userName) {
		this.userDao.updateUser(userId, userName);
	}
}
