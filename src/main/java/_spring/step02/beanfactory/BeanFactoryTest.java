package _spring.step02.beanfactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@SuppressWarnings("deprecation")
public class BeanFactoryTest {

	public static void main(String[] args) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource res = resolver.getResource("classpath:_spring/step02/beanfactory/beans.xml");
		BeanFactory bf = new XmlBeanFactory(res);
		
		Car car = bf.getBean("car1", Car.class);
		System.out.println(car.getBrand());
		
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(res);
		car = factory.getBean("car1", Car.class);
		System.out.println(car.getBrand());
	}
}
