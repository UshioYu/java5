package com.ushio.framework.po;

import com.ushio.wework.util.LogHelper;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class PoBasePage {

    private String name;
    private HashMap<String, List<HashMap<String,Object>>> methods;

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
        methods.get(poMethod).forEach(step -> {
            step.entrySet().forEach(entry -> {
                String key = entry.getKey();
                Object value = entry.getValue();
                LogHelper.info("runMethod() forEach key:" + key + ",value:" + value);
                switch (key){
                    case "browserType":
                        String browserStr = value.toString();
                        WebDriver webDriver;
                        if ("chrome".equalsIgnoreCase(browserStr)) {
                            webDriver = new ChromeDriver();
                        } else if ("firefox".equalsIgnoreCase(browserStr)) {
                            webDriver = new FirefoxDriver();
                        } else {
                            //设个默认值
                            webDriver = new ChromeDriver();
                        }
                        PoPageHelper.getInstance().setWebDriver(webDriver);
                        break;
                    case "implicitlyWait":
                        PoPageHelper.getInstance().getWebDriver().manage().timeouts().implicitlyWait((Integer) value, TimeUnit.SECONDS);
                        break;
                    case "get":
                        PoPageHelper.getInstance().getWebDriver().get(value.toString());
                        break;
                    case "find":
                        List<String> finds = (ArrayList<String>) value;
                        String findBy = finds.get(0);
                        String findLocator = finds.get(1);
                        if ("id".equalsIgnoreCase(findBy)) {
                            by.set(By.id(findLocator));
                        } else if ("css".equalsIgnoreCase(findBy)) {
                            by.set(By.cssSelector(findLocator));
                        } else if ("linkText".equalsIgnoreCase(findBy)) {
                            by.set(By.linkText(findLocator));
                        }
                        break;
                    case "action":
                        List<String> actions = (ArrayList<String>) value;
                        String actionKey = actions.get(0);
                        if ("click".equalsIgnoreCase(actionKey)) {
                            PoPageHelper.getInstance().getWebDriver().findElement(by.get()).click();
                        } else if ("sendkeys".equalsIgnoreCase(actionKey)) {
                            String actionValue = actions.get(1);
                            if (actionValue.startsWith("Keys.")) {
                                if ("Keys.ENTER".equalsIgnoreCase(actionValue)) {
                                    PoPageHelper.getInstance().getWebDriver().findElement(by.get()).sendKeys(Keys.ENTER);
                                }
                            } else {
                                PoPageHelper.getInstance().getWebDriver().findElement(by.get()).sendKeys(actionValue);
                            }
                        }
                        break;
                    case "getText":
                        break;
                    case "assertThat":
                        break;
                    default:
                        break;
                }
            });
        });
    }
}
