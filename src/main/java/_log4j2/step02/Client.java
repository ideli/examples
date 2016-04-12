package _log4j2.step02;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class Client {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		//自定义配置文件路径
		File file = new File(Client.class.getResource("/").getPath() + "_log4j2/step02/log4j2.xml");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		final ConfigurationSource source = new ConfigurationSource(in);
		Configurator.initialize(null, source);
		
		//WEB下在web.xml中添加
		//<context-param>
		//	<param-name>log4jConfiguration</param-name>
		//	<param-value>/WEB-INF/conf/log4j2.xml</param-value>
		//</context-param>
		//<listener>
		//	<listener-class>org.apache.logging.log4j.web.Log4jServletContextListener</listener-class>
		//</listener>
		
		Logger logger = LogManager.getLogger("mylog");
		//触发文件尺寸达到上限
		boolean setSizeRolling = false;
		boolean setTimeRolling = false;
		if(setSizeRolling) {
			for(int i = 0; i < 50000; i++) {
				logger.trace("trace message");
				logger.debug("debug message");
				logger.info("info message");
				logger.warn("warn message");
				logger.error("error message");
				logger.fatal("fatal message");
			}
		}
		//触发时间条件
		if(setTimeRolling)
			Thread.sleep(1000 * 61);
		logger.trace("trace message");
		logger.debug("debug message");
		logger.info("info message");
		logger.warn("warn message");
		logger.error("error message");
		logger.fatal("fatal message");
		
		Logger asyncLogger = LogManager.getLogger("asynclog");
		asyncLogger.trace("trace message");
		asyncLogger.debug("debug message");
		asyncLogger.info("info message");
		asyncLogger.warn("warn message");
		asyncLogger.error("error message");
		asyncLogger.fatal("fatal message");
	}
}
