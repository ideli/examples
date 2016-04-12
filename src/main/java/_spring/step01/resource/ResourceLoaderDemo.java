package _spring.step01.resource;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/*
 * 使用资源加载器(不显示使用Resource)
 */
public class ResourceLoaderDemo {

	public static void main(String[] args) throws IOException {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] ress = resolver.getResources("classpath*:_spring/step01/resource/test.xml");
		for(Resource res : ress) {
			System.out.println(res.getDescription());
		}
	}
}
