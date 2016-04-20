package _spring.test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

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

/**
 * 单元测试 -> 集成测试 -> 功能测试 -> 系统测试
 *
 */
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
	
	//断言assertThat
	@Test
	public void testAssertThat() {
		//assertThat(T actual, Matcher matcher)  actual - 想要验证的值,matcher - Hamcrest表达式
		int x = 50;
		assertThat(x, greaterThan(20));
		assertThat(x, greaterThanOrEqualTo(50));
		assertThat(x, lessThan(80));
		assertThat(x, lessThanOrEqualTo(50));
		assertThat(x, is(50));
		assertThat(x, not(100));
		//所有条件都必须成立
		assertThat(x, allOf(greaterThan(20), lessThan(80)));
		//只要有一个条件成功
		assertThat(x, anyOf(greaterThan(80), lessThan(80)));
		//无论什么条件测试都通过
		assertThat(x, anything());
		
		assertThat(testString, containsString("test"));
		assertThat(testString, startsWith("test"));
		assertThat(testString, endsWith("ing"));
		assertThat(testString, equalTo("test string"));
		assertThat(testString, equalToIgnoringCase("Test String"));
		assertThat(testString, equalToIgnoringWhiteSpace(" test string "));
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
