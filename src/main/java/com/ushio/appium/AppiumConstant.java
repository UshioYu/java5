package com.ushio.appium;

import io.appium.java_client.remote.MobilePlatform;

public class AppiumConstant {

    public static final String DEVICE_NAME = "Android Emulator";
    public static final String APP_PACKAGE = "xx.xxxxx.xxx";
    public static final String APP_ACTIVITY = ".xxxxx";
    public static final String PLATFORM_NAME = MobilePlatform.ANDROID;
    public static final boolean NO_RESET = true;
    public static final boolean DONT_STOP_APP_ON_RESET = true;
    public static final String URL = "http://127.0.0.1:4723/wd/hub";
    public static final int IMPLICITLY_WAIT = 30;
    //暂时通过静态变量控制是否已经登录的状态，正常应该登录成功之后维持登录状态
    //可通过与developer约定协议比如：sharedperfences里面存值或者改变系统属性值，保证appium可以获取即可
    public static final boolean IS_LOGINED = true;

}
