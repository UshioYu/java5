package com.ushio.framework.po;

import com.ushio.wework.util.LogHelper;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@Data
public class PoTestCase {

    private String name;
    private List<HashMap<String,Object>> steps;

    public void run(){
        steps.forEach(step -> {
            step.entrySet().forEach(entry -> {
                String key = entry.getKey();
                Object value = entry.getValue();
                LogHelper.info("run() key:" + key+",value:"+value);
                String[] keyStrlist = key.split("\\.");
                if (keyStrlist.length > 1) {
                    //key列有多个，比如main_page.new之类的
                    String poName = keyStrlist[0];
                    String poMethod = keyStrlist[1];
                    LogHelper.info("run() poName:" + poName + ",poMethod:" + poMethod);
                    if ("new".equals(poMethod)) {
                        PoBasePage currentPage = PoPageHelper.getInstance().load(String.valueOf(value));
                        PoPageHelper.getInstance().putPoBasePage(poName, currentPage);
                    } else {
                        //已经new完成，直接执行page的方法
                        PoPageHelper.getInstance().getPoBasePage(poName).runMethod(poMethod);
                    }
                } else {
                    //key列只有一个，暂时不处理
                    LogHelper.info("only has one key!");
                }
            });
        });
    }
}
