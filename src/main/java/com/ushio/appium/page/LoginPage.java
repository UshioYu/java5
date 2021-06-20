package com.ushio.appium.page;

import com.ushio.appium.ByLocator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Pattern;

public class LoginPage extends BasePage{

    public LoginPage(AppiumDriver<MobileElement> driver, WebDriverWait webDriverWait) {
        super(driver,webDriverWait);
    }

    public HomePage login(String phoneNum){
        sendKeys(ByLocator.id.loginEditPhoneNum,phoneNum);
        if ("发送验证码".equals(driver.findElement(ByLocator.id.loginTextSendCode).getText())) {
            click(ByLocator.id.loginTextSendCode);
        }
        Pattern pattern = Pattern.compile("^\\d{4}$");
        try {
            if (webDriverWait.until(ExpectedConditions.textMatches(ByLocator.id.loginEditCode,pattern))) {
                click(ByLocator.id.loginTextLogin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //这里应该等待登录验证成功后再退出，后续有时间再进行补充
//        if (driver.findElement(ByLocator.id.loginTextInfo).getText().contains("错误")) {
//        }
        return new HomePage(driver,webDriverWait);
    }

}
