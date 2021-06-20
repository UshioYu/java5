package com.ushio.appium;

import com.ushio.appium.page.LaunchPage;
import com.ushio.util.ThreadUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author: ushio
 * @description:app入口类，单例类
 **/
public class AppEntrance {

    private volatile static AppEntrance appEntrance;

    private static AppiumDriver<MobileElement> appiumDriver;
    private static WebDriverWait webDriverWait;

    public static AppEntrance getInstance() {
        if (appEntrance == null) {
            synchronized (AppEntrance.class) {
                if (appEntrance == null) {
                    appEntrance = new AppEntrance();
                }
            }
        }
        return appEntrance;
    }

    private AppEntrance(){
        initWebDriver();
    }

    private void initWebDriver() {
        if (appiumDriver == null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, AppiumConstant.DEVICE_NAME);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, AppiumConstant.APP_PACKAGE);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, AppiumConstant.APP_ACTIVITY);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, AppiumConstant.PLATFORM_NAME);
            capabilities.setCapability(MobileCapabilityType.NO_RESET, AppiumConstant.NO_RESET);
//        capabilities.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, true);
            try {
                appiumDriver = new AppiumDriver<>(new URL(AppiumConstant.URL),
                        capabilities);
                appiumDriver.manage().timeouts().implicitlyWait(AppiumConstant.IMPLICITLY_WAIT, TimeUnit.SECONDS);
                webDriverWait = new WebDriverWait(appiumDriver, 60,1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void quit() {
        if (appiumDriver != null) {
            ThreadUtil.sleep(15000);
            appiumDriver.quit();
        }
    }

    public LaunchPage launchApp(){
        return new LaunchPage(appiumDriver,webDriverWait);
    }

}
