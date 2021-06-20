package com.ushio.appium.page;

import com.ushio.appium.ByLocator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ScreenPage extends BasePage {
    public ScreenPage(AppiumDriver<MobileElement> driver, WebDriverWait webDriverWait) {
        super(driver, webDriverWait);
    }

    public ScreenPage filter(List<String> stringList){
        stringList.stream().forEach(s -> {
            click(By.cssSelector("*[text*='" + s + "']"));
        });
       return this;
    }

    public String getFilterData(){
        return getText(ByLocator.id.screenResultText);
    }

}
