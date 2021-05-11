package com.ushio.wework.selenium;

import org.openqa.selenium.By;

/**
 * @author: ushio
 * @description:By各静态变量
 **/
public class ByLocator {

    public @interface id {
        By menu_contacts = By.id("menu_contacts");
        By memberSearchInput = By.id("memberSearchInput");
        By party_name = By.id("party_name");
    }

    public @interface linkText {
        By addMember = By.linkText("添加成员");
        By add = By.linkText("添加");
        By addDepartment = By.linkText("添加部门");
        By addSubDepartment = By.linkText("添加子部门");
        By deleteSubDepartment = By.linkText("删除");
        By updateDepartment = By.linkText("修改名称");
        By chooseDepartment = By.linkText("选择所属部门");
        By confirm = By.linkText("确定");
        By save = By.linkText("保存");
        By saveAndAdd = By.linkText("保存并继续添加");
        By cancel = By.linkText("取消");
    }

    public @interface name {
        By name = By.name("name");
        By username = By.name("username");
        By english_name = By.name("english_name");
        By acctid = By.name("acctid");
        By gender = By.name("gender");
        By mobile = By.name("mobile");
        By ext_tel = By.name("ext_tel");
        By alias = By.name("alias");
        By xcx_corp_address = By.name("xcx_corp_address");
        By position = By.name("position");
        By identity_stat = By.name("identity_stat");
        By extern_position_set = By.name("extern_position_set");
        By sendInvite = By.name("sendInvite");
    }

    public @interface cssSelector {
        By js_party_info = By.cssSelector(".js_party_info");
        By spanIconJstreeContextmenuHover = By.cssSelector("span.icon.jstree-contextmenu-hover");
    }

    public @interface xpath {
        By spanIconJstreeContextmenuHover = By.xpath("span[@class='icon jstree-contextmenu-hover']");
    }

}
