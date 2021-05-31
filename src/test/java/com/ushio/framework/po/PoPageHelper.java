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

    private HashMap<String,PoBasePage> poBasePageHashMap = new HashMap<>();

    public void putPoBasePage(String key, PoBasePage poBasePage) {
        poBasePageHashMap.put(key, poBasePage);
    }

    public PoBasePage getPoBasePage(String key) {
        if (!TextUtils.isEmpty(key) && poBasePageHashMap.containsKey(key)) {
            return poBasePageHashMap.get(key);
        }
        return null;
    }

    /**
     * 从po的yaml文件中读取数据，并生成一个BasePage的实例
     */
    public PoBasePage load(String name) {
        String path = String.format("src/test/resources/testcase/%s.yaml", name);
        PoBasePage page = loadFromFile(path);
        return page;
    }

    public PoBasePage loadFromFile(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            return mapper.readValue(new File(path), PoBasePage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
