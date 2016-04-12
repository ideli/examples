package _spring.step07.transaction.xml;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void addUser(String userName) {
		int userId = this.userDao.getMaxUserId();
		this.userDao.addUser(userId, userName);
		//如果演示异常回滚, 则插入重复的userId, 触发重复键错误
		boolean makeError = false;
		if(!makeError) {
			userId++;
		}
		this.userDao.addUser(userId, userName + "_copy");
	}

	@Override
	public void updateUser(int userId, String userName) {
		this.userDao.updateUser(userId, userName);
	}

	@Override
	public String getUserName(int userId) {
		return this.userDao.getUserName(userId);
	}
}
