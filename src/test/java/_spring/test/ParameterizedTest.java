package _spring.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 参数化测试
 * 1 指定特殊的运行器
 * 2 声明变量, 分别用于存储期望值和测试数据
 * 3 创建一个带参数的构造函数
 * 4 创建一个静态测试数据供给方法, 返回类型Collection, 并添加@Parameters注解
 *
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {

	private String testString;
	private String expectedString;
	
	public ParameterizedTest(String testString, String expectedString) {
		this.testString = testString;
		this.expectedString = expectedString;
	}
	
	@Parameters
	public static Collection getParameters() {
		String[][] object = {
				{ "a", "a" },
				{ "b", "b" },
				{ "c", "c" }
		};
		return Arrays.asList(object);
	}
	
	@Test
	public void simpleTest() {
		assertEquals(this.testString, this.expectedString);
	}
}
