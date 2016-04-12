package _aop.step01.jdkproxy;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("UserServiceImpl:addUser");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUser() {
		System.out.println("UserServiceImpl:removeUser");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
