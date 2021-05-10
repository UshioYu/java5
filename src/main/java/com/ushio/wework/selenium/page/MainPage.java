package com.ushio.wework.selenium.page;

import com.ushio.wework.selenium.ByConstant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author: ushio
 * @description:
 **/
public class MainPage extends BasePage{

    public MainPage() {
        super();
    }

    public MainPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver,webDriverWait);
    }

    public ContactPage goToContact() {
        click(ByConstant.menuContacts);
        return new ContactPage(webDriver, webDriverWait);
    }

    public ContactPage goToAddMember() {
        click(ByConstant.addMember);
        return new ContactPage(webDriver, webDriverWait);
    }
}
