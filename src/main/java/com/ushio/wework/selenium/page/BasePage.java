package com.ushio.wework.selenium.page;

import com.ushio.util.LogHelper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author: ushio
 * @description:Page基类，用于提取各种公共方法
 **/
public class BasePage {

    public WebDriver webDriver;
    public WebDriverWait webDriverWait;
    private Integer retryTimes = 3;

    public BasePage(){

    }

    public BasePage(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }

    public void click(By by){
        try {
            webDriver.findElement(by).click();
        } catch (Exception exception) {
            LogHelper.error(by + "click error!", exception);
            retryTimes += 1;
            if (retryTimes < 4) {
                //处理弹窗
                handleAlert();
                click(by);
            } else {
                retryTimes = 0;
            }
        }
    }

    private void handleAlert(){
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    public void sendKeys(By by, String keys) {
        webDriver.findElement(by).sendKeys(keys);
    }

}

