package com.ushio.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j log管理类
 */
public class LogHelper {
    public static final Logger logger = LoggerFactory.getLogger(LogHelper.class);

    public static void trace(String msg){
        logger.trace(msg);
    }

    public static void trace(String msg, Throwable throwable) {
        logger.trace(msg, throwable);
    }

    public static void debug(String msg){
        logger.debug(msg);
    }

    public static void debug(String msg, Throwable throwable) {
        logger.debug(msg, throwable);
    }

    public static void info(String msg){
        logger.info(msg);
    }

    public static void info(String msg, Throwable throwable) {
        logger.info(msg, throwable);
    }

    public static void warn(String msg){
        logger.warn(msg);
    }

    public static void warn(String msg, Throwable throwable) {
        logger.warn(msg, throwable);
    }

    public static void error(String msg){
        logger.error(msg);
    }

    public static void error(String msg, Throwable throwable) {
        logger.error(msg, throwable);
    }

}
