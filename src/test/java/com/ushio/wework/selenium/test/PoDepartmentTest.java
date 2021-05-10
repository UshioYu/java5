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
public class PoDepartmentTest {

    @Test
    void menuContactsTabTest() {
        WebEntrance.getInstance().login().goToContact();
    }

    @Test
    void addDepartTest() {
        String departName = "java5";
        String parentDepartName = "独立人";
        assertTrue(WebEntrance.getInstance().login().goToContact().addDepart(departName,parentDepartName).searchDepart(departName).getPartyInfo().contains(departName));
    }

    @Test
    void addSubDepartTest() {
        assertTrue(WebEntrance.getInstance().login().goToContact().addSubDepart("java6","subjava6","subsubjava6")
                .searchDepart("subsubjava6").getPartyInfo().contains("subsubjava6"));
    }

    @Test
    void deleteDepartTest() {
        assertTrue(!(WebEntrance.getInstance().login().goToContact().deleteDepart("java6", "subjava6", "subsubjava6")
                .searchDepart("subsubjava6").getPartyInfo().contains("subsubjava6")));
    }

    @Test
    void addDepartNameTest() {
        String departName = "java6";
        String parentDepartName = "独立人";
        assertThat(departName,equalTo(WebEntrance.getInstance().login().goToContact().addDepart(departName,parentDepartName).searchDepart(departName).getPartyName()));
    }

    @AfterAll
    public static void afterAll(){
        //WebEntrance.getInstance().quit();
    }
}
