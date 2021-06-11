package com.ushio.framework.po;

import com.ushio.util.LogHelper;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Data
public class PoTestCase {

    private String name;
    private List<HashMap<String,Object>> steps;
    private List<AssertModel> asserts;

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
                    //key列只有一个
                    LogHelper.info("only has one key!");
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
