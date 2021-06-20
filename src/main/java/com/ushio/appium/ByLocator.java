package com.ushio.appium;

import org.openqa.selenium.By;

/**
 * @author: ushio
 * @description:By各静态变量
 **/
public class ByLocator {

    public @interface id {
        By loginEditPhoneNum = By.id("editPhoneNum");
        By loginTextSendCode = By.id("textSendCode");
        By loginEditCode = By.id("editCode");
        By loginTextLogin = By.id("textLogin");
        By loginTextInfo = By.id("textInfo");
        By searchNameEt = By.id("search_name_et");
        By searchContentSearchRv = By.id("search_content_search_rv");
        By searchScreenBtTxt = By.id("search_screen_bt_txt");
        By screenResultText = By.id("screen_result_text");

    }

    public @interface linkText {
    }

    public @interface name {
        By name = By.name("name");

    }

    public @interface cssSelector {
       By search = By.cssSelector("*[text*='搜索']");
    }

    public @interface xpath {
        By searchTitle = By.xpath("//*[@resource-id='title']");

    }

}
