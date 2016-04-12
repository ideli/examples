package _log4j2.step01;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Client {

	static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	
	public static void main(String[] args) {
		//jar: log4j-core, log4j-api, WEB项目需要 log4j-web
		//配置文件名称为log4j2.xml, 放在src目录下, 如果没有按默认设置(参见当前目录log4j2.xml)
		//TRACE < DEBUG < INFO < WARN < ERROR < FATAL
		logger.trace("trace message");
		logger.debug("debug message");
		logger.info("info message");
		logger.warn("warn message");
		logger.error("error message");
		logger.fatal("fatal message");
	}
}
