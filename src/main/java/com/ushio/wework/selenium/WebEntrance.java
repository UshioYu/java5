package com.ushio.wework.selenium;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ushio.wework.selenium.page.MainPage;
import com.ushio.wework.util.Constant;
import com.ushio.wework.util.ThreadUtil;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: ushio
 * @description:web入口类，单例类
 **/
public class WebEntrance {

    private volatile static WebEntrance webEntrance;

    private static WebDriver webDriver;
    private static WebDriverWait webDriverWait;

    public static WebEntrance getInstance() {
        if (webEntrance == null) {
            synchronized (WebEntrance.class) {
                if (webEntrance == null) {
                    webEntrance = new WebEntrance();
                }
            }
        }
        return webEntrance;
    }

    private WebEntrance(){
        initWebDriver();
    }

    private void initWebDriver() {
        //todo ushio 这里可以扩展传参，加入参数化
        if (webDriver == null) {
            if ("chrome".equalsIgnoreCase(System.getenv("browser"))) {
                webDriver = new ChromeDriver();
            } else if ("firefox".equalsIgnoreCase(System.getenv("browser"))) {
                webDriver = new FirefoxDriver();
            } else {
                //设个默认值
                webDriver = new ChromeDriver();
            }
        }
        //time也可提出参数，在调用的地方使用
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriverWait = new WebDriverWait(webDriver, 10,1000);
        webDriver.get(Constant.SELENIUM_BASEURL);
        webDriver.manage().window().maximize();
    }

    public void quit() {
        if (webDriver != null) {
            ThreadUtil.sleep(15000);
            webDriver.quit();
        }
    }

    public MainPage login(){
        File file = new File("cookies.yaml");
        if (file.exists()) {
            //读取yaml的cookies，塞给driver，自动登录（这里也要考虑cookies过期重新扫码生成，删除旧的xml文件）
            List<HashMap<String,Object>> cookies = new ArrayList<>();
            try {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                TypeReference typeReference = new TypeReference<ArrayList<HashMap<String,Object>>>() {};
                cookies = (ArrayList<HashMap<String,Object>>)mapper.readValue(file,typeReference);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cookies.forEach(cookieMap->{
                Cookie cookie = new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString());
                webDriver.manage().addCookie(cookie);
            });
        } else {
            //等待手机扫码，扫码后保存cookies
            ThreadUtil.sleep(15000);
            Set<Cookie> cookies = webDriver.manage().getCookies();
            try {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                mapper.writeValue(new File("cookies.yaml"), cookies);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        webDriver.navigate().refresh();
        return new MainPage(webDriver,webDriverWait);
    }

}
