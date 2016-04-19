package _spring.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
public class ExampleTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	@Qualifier("testString")
	private String testString;
	
	@Test
	public void test1() {
		assertTrue(testString.equals("test string"));
	}
	
	//异常测试: 抛出指定异常则测试通过
	@Test(expected=NullPointerException.class)
	public void test2() {
		String s = new HashMap<String, String>().get("s");
		System.out.println(s.length());
	}
	
	//超时测试: 在指定的毫秒数内完成则测试通过
	@Test(timeout=10)
	public void test3() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//只可以出现一次, 在所有测试方法前执行
	@BeforeClass
	public static void beforeClass() {
		System.out.println("beforeClass executed");
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("afterClass executed");
	}
	
	@Before
	public void before1() {
		System.out.println("before1 executed");
	}
	
	@Before
	public void before2() {
		System.out.println("before2 executed");
	}
	
	@After
	public void after1() {
		System.out.println("after1 executed");
	}
	
	@After
	public void after2() {
		System.out.println("after2 executed");
	}
}
