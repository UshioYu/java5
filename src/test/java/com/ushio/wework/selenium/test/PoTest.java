package com.ushio.wework.selenium.test;

import com.ushio.wework.selenium.WebEntrance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author: ushio
 * @description:
 **/
public class PoTest {

    @Test
    void menuContactsTabTest() {
        WebEntrance.getInstance().login().click(By.id("menu_contacts"));
    }

    @Test
    void addDepartTest() {
        String departName = "java5";
        assertTrue(WebEntrance.getInstance().login().goToContact().addDepart(departName).searchDepart(departName).getPartyInfo().contains(departName));
    }

    @Test
    void addDepartNameTest() {
        String departName = "java6";
        assertThat(departName,equalTo(WebEntrance.getInstance().login().goToContact().addDepart(departName).searchDepart(departName).getPartyName()));
    }

    @AfterAll
    public static void afterAll(){
        WebEntrance.getInstance().quit();
    }
}
