package com.ushio.appium.page;

import com.ushio.appium.AppiumConstant;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LaunchPage extends BasePage{

    public LaunchPage(AppiumDriver<MobileElement> appiumDriver, WebDriverWait webDriverWait) {
        super(appiumDriver,webDriverWait);
    }

    public HomePage toHome() {
        return new HomePage(driver, webDriverWait);
    }

    public HomePage toHome(String phoneNum){
        if(AppiumConstant.IS_LOGINED){
            return new HomePage(driver,webDriverWait);
        }
        return new LoginPage(driver,webDriverWait).login(phoneNum);
    }
}
