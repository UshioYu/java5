package com.ushio.appium.page;

import com.ushio.appium.ByLocator;
import com.ushio.util.LogHelper;
import com.ushio.util.PyUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.windows.PressesKeyCode;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPage extends BasePage{

    public SearchPage(AppiumDriver<MobileElement> appiumDriver, WebDriverWait webDriverWait) {
        super(appiumDriver,webDriverWait);
    }

    public String doSearchResultStr(String searchText){
        //todo 搜索框如何响应sendkeys，需要和开发进行约定，可能需要修改控件为editText
        sendKeys(ByLocator.id.searchNameEt,searchText);
        String realText = driver.findElements(ByLocator.xpath.searchTitle).get(1).getText();
        LogHelper.info("doSearch realText:" + realText);
        //todo 算法方法需要修改只匹配首大写字母
        String pyText = PyUtil.getChineseToPinyin(realText);
        LogHelper.info("doSearch pyText:" + pyText);
        return pyText;
    }

    public ScreenPage toScreen(){
        click(ByLocator.id.searchScreenBtTxt);
        return new ScreenPage(driver,webDriverWait);
    }

}
