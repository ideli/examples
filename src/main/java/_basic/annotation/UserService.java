package _basic.annotation;

/**
 * 注解赋值演示
 * 一个成员 @AnnoExapmle("John")
 * 多个成员 @AnnoExapmle(id=1,name="John",date="2016-01-01")
 * 数组成员 @AnnoExapmle(value={"111","222"})
 *
 */
public class UserService {

	@NeedTest(true)
	public void addUser() {}
	
	@NeedTest(false)
	public void delUser() {}
}
