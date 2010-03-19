package sily.test;

import org.apache.log4j.Logger;

public class Test {

	public static void main(String[] args) {
		Logger syslogger = Logger.getLogger(Test.class.getName());
		syslogger.debug("debug____111111");
		syslogger.info("info___111111");
		syslogger.warn("warn____111111");
		syslogger.error("error__111111");
	}
}
