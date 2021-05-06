package com.ushio.wework.util;

/**
 * @author: ushio
 * @description:线程管理类
 **/
public class ThreadUtil {

    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LogHelper.error("ThreadUtil.sleep", e);
        }
    }
}
