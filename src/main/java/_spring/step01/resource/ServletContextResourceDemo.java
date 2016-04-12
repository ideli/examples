package _spring.step01.resource;

public class ServletContextResourceDemo {

	public static void main(String[] args) {
		//文件资源地址以相对于Web应用根路径的方式表示
		//需要在web项目中执行,仅记录写法
		/*
		Resource res = new ServletContextResource(application, "/WEB-INF/conf/config.xml");
		WebUtils.getTempDir(application).getAbsolutePath();
		*/
	}
}
