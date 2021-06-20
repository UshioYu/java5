package com.ushio.appium.testcase;

import com.ushio.appium.AppEntrance;
import com.ushio.util.LogHelper;
import com.ushio.util.PyUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @Test
    void screenTest(){
        List<String> strings = new ArrayList<>();
        strings.add("电视剧");
        strings.add("免费");
        strings.add("喜剧");
        String filterData = AppEntrance.getInstance().launchApp().toHome().toSearch()
                .toScreen().filter(strings).getFilterData();
        List<Executable> executables = new ArrayList<>();
        strings.stream().forEach(s -> {
            Executable executable = () -> assertThat(filterData, containsString(s));
            executables.add(executable);
        });
        assertAll("筛选批量断言",executables);
    }
}
