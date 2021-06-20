package com.ushio.appium.framework.po;

import com.ushio.appium.AppiumConstant;
import com.ushio.util.LogHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Data
public class PoBasePage {

    private String name;
    private HashMap<String, List<HashMap<String,Object>>> methods;
    private List<AssertModel> asserts;

    public void runMethod(String poMethod){
        if (methods == null) {
            try {
                this.getClass().getMethod(poMethod).invoke(this, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        //AtomicReference在外层定义使用
        AtomicReference<By> by = new AtomicReference<>();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        methods.get(poMethod).forEach(step -> {
            step.entrySet().forEach(entry -> {
                String key = entry.getKey();
                Object value = entry.getValue();
                LogHelper.info("runMethod() forEach key:" + key + ",value:" + value);
                switch (key){
                    case "deviceName":
                        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, value);
                        break;
                    case "appPackage":
                        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, value);
                        break;
                    case "appActivity":
                        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, value);
                        break;
                    case "platformName":
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, value);
                        break;
                    case "noReset":
                        capabilities.setCapability(MobileCapabilityType.NO_RESET, (boolean) value);
                        break;
                    case "URL":
                        try {
                            AppiumDriver<MobileElement> appiumDriver = new AppiumDriver<>(new URL(AppiumConstant.URL),
                                    capabilities);
                            PoPageHelper.getInstance().setAppiumDriver(appiumDriver);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "implicitlyWait":
                        PoPageHelper.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait((Integer) value, TimeUnit.SECONDS);
                        break;
                    case "find":
                        List<String> finds = (ArrayList<String>) value;
                        String findBy = finds.get(0);
                        String findLocator = finds.get(1);
                        if ("id".equalsIgnoreCase(findBy)) {
                            by.set(By.id(findLocator));
                        } else if ("css".equalsIgnoreCase(findBy)) {
                            by.set(By.cssSelector(findLocator));
                        }
                        break;
                    case "action":
                        List<String> actions = (ArrayList<String>) value;
                        String actionKey = actions.get(0);
                        if ("click".equalsIgnoreCase(actionKey)) {
                            PoPageHelper.getInstance().getAppiumDriver().findElement(by.get()).click();
                        } else if ("sendkeys".equalsIgnoreCase(actionKey)) {
                            String actionValue = actions.get(1);
                            PoPageHelper.getInstance().getAppiumDriver().findElement(by.get()).sendKeys(actionValue);
                        }
                        break;
                    case "getText":
                        String text = PoPageHelper.getInstance().getAppiumDriver().findElement(by.get()).getText();
                        PoPageHelper.getInstance().putAssert(String.valueOf(value), text);
                        break;
                    default:
                        break;
                }
            });
        });
        if (asserts != null) {
            ArrayList<Executable> assertList = new ArrayList<>();
            asserts.stream().forEach(assertModel -> {
                String actual = assertModel.getActual();
                String text = String.valueOf(PoPageHelper.getInstance().getAssert(actual));
                LogHelper.info("asserts actual:" + actual + ",text:" + text);
                assertList.add(()->{
                    assertThat(assertModel.getReason(), text,
                            containsString(assertModel.getExpect()));
                        });
            });
            assertAll("",assertList.stream());
        }
    }
}
