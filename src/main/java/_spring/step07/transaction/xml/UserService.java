package _spring.step07.transaction.xml;

public interface UserService {

	void addUser(String userName);
	void updateUser(int userId, String userName);
	String getUserName(int userId);
}
