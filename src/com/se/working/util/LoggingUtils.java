package com.se.working.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * log4j方法怎么弄了那么多参数！！
 * @author BO
 *
 */
public class LoggingUtils {
	private static final Logger LOGGER = createLogger();
	 private static Logger createLogger() {
		 Logger logger = LogManager.getLogger("SEWM");
		 return logger;
	 }
	 
	 public static void info(Object message) {
		 LOGGER.info(message);
	 }
	 
	 public static void warn(Object message) {
		 LOGGER.warn(message);
	 }
	 
}
