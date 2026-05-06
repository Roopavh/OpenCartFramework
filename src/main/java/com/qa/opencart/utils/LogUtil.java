package com.qa.opencart.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

	private static final Logger log = LogManager.getLogger(LogUtil.class);

	public static void info(String msg) {
		log.info(msg);
	}

	public static void error(String msg) {
		log.error(msg);
	}

	public static void warn(String msg) {
		log.warn(msg);
	}

	public static void fatal(String msg) {
		log.fatal(msg);
	}

}
