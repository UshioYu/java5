package com.ushio.wework.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author: ushio
 * @description:
 **/
public class ContactPage extends BasePage {

    private By partyInfo = By.cssSelector(".js_party_info");
    private By partyName = By.id("party_name");

    public ContactPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public ContactPage addDepart(String departmentName) {
        click(By.linkText("添加"));
        click(By.linkText("添加部门"));
        sendKeys(By.name("name"), departmentName);
        click(By.linkText("选择所属部门"));
        webDriver.findElements(By.linkText("独立人")).get(1).click();
        click(By.linkText("确定"));
        return this;
    }

    public ContactPage searchDepart(String departmentName) {
        click(By.id("memberSearchInput"));
        sendKeys(By.id("memberSearchInput"), departmentName);
        return this;
    }

    public ContactPage deleteDepart(String departmentName) {
        return this;
    }

    public String getPartyInfo() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(partyInfo));
        String text = webDriver.findElement(partyInfo).getText();
        System.out.println(text);
        return text;
    }

    public String getPartyName() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(partyName));
        return webDriver.findElement(partyName).getText();
    }

}
