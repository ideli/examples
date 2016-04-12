package _log4j2.step04;

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
		File file = new File(Client.class.getResource("/").getPath() + "_log4j2/step04/log4j2.xml");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		final ConfigurationSource source = new ConfigurationSource(in);
		Configurator.initialize(null, source);
		
		Logger logger = LogManager.getLogger("mongolog");
		logger.trace("trace message");
		logger.debug("debug message");
		logger.info("info message");
		logger.warn("warn message");
		logger.error("error message");
		logger.fatal("fatal message");
	}
}
