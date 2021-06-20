package com.ushio.appium.page;

import com.ushio.appium.ByLocator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage{

    public HomePage(AppiumDriver<MobileElement> appiumDriver, WebDriverWait webDriverWait) {
        super(appiumDriver,webDriverWait);
    }

    public SearchPage toSearch(){
        click(ByLocator.cssSelector.search);
        return new SearchPage(driver,webDriverWait);
    }

}
