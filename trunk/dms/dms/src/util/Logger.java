package util;

import org.apache.log4j.PropertyConfigurator;
import comm.*;
import java.io.*;
import java.util.Properties;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Logger {
    private static Logger logger;
    private static org.apache.log4j.Logger runtimeInfo;
    private static String LOG_CONFIG_FILE = "log4j.properties";
    private static String LOG_INFO_TAG = "INFO";

    private Logger() {
        try{
            InputStream is = Logger.class.getClassLoader().getResourceAsStream(
                    LOG_CONFIG_FILE);
            Properties p = new Properties();
            p.load(is);
            PropertyConfigurator.configure(p);
            runtimeInfo = org.apache.log4j.Logger.getLogger(LOG_INFO_TAG);
        }catch(Exception e){
            e.printStackTrace();
            runtimeInfo=org.apache.log4j.Logger.getLogger("Logger");
        }
    }

    public synchronized static org.apache.log4j.Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return runtimeInfo;
    }
}
