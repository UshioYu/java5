package com.ushio.appium.page;

import com.ushio.util.LogHelper;
import com.ushio.wework.selenium.ByLocator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    public AppiumDriver<MobileElement> driver;
    public WebDriverWait webDriverWait;
    Integer retryTimes = 3;

    public BasePage(AppiumDriver<MobileElement> driver,WebDriverWait webDriverWait) {
        this.driver = driver;
        this.webDriverWait = webDriverWait;
    }

    public boolean click(By by) {
        try {
            LogHelper.info("click " + by);
            driver.findElement(by).click();
            LogHelper.info("click success " + by);
        } catch (Exception e) {
            LogHelper.info("driver.getPageSource():" + driver.getPageSource());
            e.printStackTrace();
            retryTimes += 1;
            if (retryTimes < 4) {
                this.handleAlert();
                return click(by);
            } else {
                retryTimes = 0;
                return false;
            }

        }
        return true;
    }

    public void sendKeys(By by, String content) {
        driver.findElement(by).sendKeys(content);
    }

    public void handleAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
