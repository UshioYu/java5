package com.ushio.framework.po;

import com.ushio.wework.util.LogHelper;
import org.openqa.selenium.By;

public class PoSearchPage extends PoBasePage{

    public String getFirstTitle(){
        String text = PoPageHelper.getInstance().getWebDriver().findElement(By.cssSelector(".topic-title")).getText();
        LogHelper.info("getFirstTitle:"+text);
        return text;
    }
}
