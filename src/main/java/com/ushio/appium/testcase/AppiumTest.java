package com.ushio.appium.testcase;

import com.ushio.appium.AppEntrance;
import com.ushio.util.LogHelper;
import com.ushio.util.PyUtil;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppiumTest {

    @Test
    void loginTest(){
        String phoneNum = "18112387886";
        AppEntrance.getInstance().launchApp().toHome(phoneNum);
    }

    @Test
    void pyTest(){
        String text = "drj";
        String result = PyUtil.getChineseToPinyin("狄仁杰");
        LogHelper.info("pyTest text:"+text+",result:"+result);
        assertThat("字母输入断言", result, containsString(text));
    }

    @Test
    void searchTest(){
        String text = "DRJ";
        String result = AppEntrance.getInstance().launchApp().toHome().toSearch().doSearchResultStr(text);
        assertThat("搜索结果进行断言", result, containsString(text));
    }
}
