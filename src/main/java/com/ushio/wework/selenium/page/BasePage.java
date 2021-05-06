package com.ushio.wework.selenium.page;

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

    public BasePage(){

    }

    public BasePage(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }

    public void click(By by){
        webDriver.findElement(by).click();
    }

    public void sendKeys(By by, String keys) {
        webDriver.findElement(by).sendKeys(keys);
    }

}

