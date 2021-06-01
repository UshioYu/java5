package com.ushio.framework.po;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.http.util.TextUtils;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PoPageHelper {

    private static volatile PoPageHelper instance;
    private HashMap<String,PoBasePage> poBasePageHashMap = new HashMap<>();
    //存储断言结果集
    private HashMap<String,Object> assertList = new HashMap<>();
    //提取出driver，整个生命周期期间只有一个变量，方便各处使用
    private WebDriver webDriver;

    public static PoPageHelper getInstance() {
        if (instance == null) {
            synchronized (PoPageHelper.class){
                if (instance == null) {
                    instance = new PoPageHelper();
                }
            }
        }
        return instance;
    }

    public void putPoBasePage(String key, PoBasePage poBasePage) {
        poBasePageHashMap.put(key, poBasePage);
    }

    public PoBasePage getPoBasePage(String key) {
        if (!TextUtils.isEmpty(key) && poBasePageHashMap.containsKey(key)) {
            return poBasePageHashMap.get(key);
        }
        return null;
    }

    public Object getAssert(String key) {
        return assertList.containsKey(key) ? assertList.get(key) : null;
    }

    public void putAssert(String key, Object assertObject) {
        assertList.put(key, assertObject);
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * 从po的yaml文件中读取数据，并生成一个BasePage的实例
     */
    public PoBasePage load(String name) {
        String path = String.format("src/test/resources/testcase/%s.yaml", name);
        File file = new File(path);
        PoBasePage page;
        if (file != null && file.exists()) {
            page = loadFromFile(file);
        } else {
            page = loadFromClassloader(name);
        }
        return page;
    }

    public PoBasePage loadFromFile(File file) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            return mapper.readValue(file, PoBasePage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用反射冲生成page实例
     **/
    public static PoBasePage loadFromClassloader(String className) {
        try {
            return (PoBasePage) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
