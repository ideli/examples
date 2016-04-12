package _aop.step04.introduction;

/**
 * 引介增强 - 目标类
 */
public class UserService {

	public void removeUser(int userId) {
		System.out.println("UserService:removeUser");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void updateUser(int userId, String userName) {
		System.out.println("UserService:updateUser");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
