package com.ushio.wework.selenium;

import org.openqa.selenium.By;

/**
 * @author: ushio
 * @description:By各静态变量
 **/
public class ByConstant {

    public static By menuContacts = By.id("menu_contacts");
    public static By addMember = By.linkText("添加成员");
    public static By add = By.linkText("添加");
    public static By addDepartment = By.linkText("添加部门");
    public static By addSubDepartment = By.linkText("添加子部门");
    public static By deleteSubDepartment = By.linkText("删除");
    public static By chooseDepartment = By.linkText("选择所属部门");
    public static By confirm = By.linkText("确定");
    public static By memberSearchInput = By.id("memberSearchInput");
}
