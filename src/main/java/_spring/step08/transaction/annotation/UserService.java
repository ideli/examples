package _spring.step08.transaction.annotation;

public interface UserService {

	void addUser(String userName);
	void updateUser(int userId, String userName);
	String getUserName(int userId);
}
