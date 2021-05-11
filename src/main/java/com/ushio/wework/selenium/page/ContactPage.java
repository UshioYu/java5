package com.ushio.wework.selenium.page;

import com.ushio.wework.selenium.ByConstant;
import com.ushio.wework.util.LogHelper;
import com.ushio.wework.util.ThreadUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

/**
 * @author: ushio
 * @description:
 **/
public class ContactPage extends BasePage {

    private By partyInfo = By.cssSelector(".js_party_info");
    private By partyName = By.id("party_name");

    public ContactPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public ContactPage addDepart(String departmentName, String parentDepartmentName) {
        click(ByConstant.add);
        click(ByConstant.addDepartment);
        sendKeys(By.name("name"), departmentName);
        click(ByConstant.chooseDepartment);
        //避免使用滚动方法，可能会有各种问题
        webDriver.findElement(By.tagName("form")).findElement(By.linkText(parentDepartmentName)).click();
        click(ByConstant.confirm);
        return this;
    }

    public ContactPage addSubDepart(String... departmentNames) {
        int i = 0;
        for (String departmentName : departmentNames) {
            LogHelper.info("departmentName:" + departmentName);
            if (departmentNames.length == 1) {
                //单独处理
                webDriver.findElements(By.cssSelector("span.icon.jstree-contextmenu-hover")).get(0).click();
                click(ByConstant.addSubDepartment);
                sendKeys(By.name("name"), departmentName);
                click(ByConstant.confirm);
                return this;
            }
            i += 1;
            LogHelper.info("i:" + i);
            if (i < departmentNames.length - 1) {
                By parentDepartmentNameBy = By.linkText(departmentName);
                click(parentDepartmentNameBy);
                //todo 这里有展开过程,最好是能定位到左侧的展开图标根据isDisplayed进行判断，无奈定位方法不对（后续有时间再研究，传参进
                // 入时默认都是有下一层，点2次暂时无妨）
//            WebElement element = webDriver.findElement(parentDepartmentNameBy).findElement(By.xpath("/../i[@class='jstree-icon jstree-ocl']"));
//            if (element != null && element.isDisplayed()) {
//                ThreadUtil.sleep(300);
//                element.click();
//            }
                ThreadUtil.sleep(300);
                click(parentDepartmentNameBy);
            } else if (i < departmentNames.length) {
                //todo 这里加次click，不然下述方法定位不到，原因未知
                By parentDepartmentNameBy = By.linkText(departmentName);
                click(parentDepartmentNameBy);
                webDriver.findElement(parentDepartmentNameBy)
                        .findElement(By.xpath("span[@class='icon jstree-contextmenu-hover']")).click();
            } else {
                click(ByConstant.addSubDepartment);
                sendKeys(By.name("name"), departmentName);
                click(ByConstant.confirm);
            }
        }
        return this;
    }

    public ContactPage searchDepart(String departmentName) {
        click(ByConstant.memberSearchInput);
        sendKeys(ByConstant.memberSearchInput, departmentName);
        return this;
    }

    /**
     * 暂时只考虑删除那种不含子部门的部门
     * @param departmentNames
     * @return
     */
    public ContactPage deleteDepart(String... departmentNames) {
        int i = 0;
        for (String departmentName : departmentNames) {
            LogHelper.info("departmentName:" + departmentName);
            i += 1;
            LogHelper.info("i:" + i);
            if (i < departmentNames.length) {
                By parentDepartmentNameBy = By.linkText(departmentName);
                click(parentDepartmentNameBy);
                ThreadUtil.sleep(300);
                click(parentDepartmentNameBy);
            } else {
                By parentDepartmentNameBy = By.linkText(departmentName);
                click(parentDepartmentNameBy);
                webDriver.findElement(parentDepartmentNameBy)
                        .findElement(By.xpath("span[@class='icon jstree-contextmenu-hover']")).click();
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ByConstant.deleteSubDepartment)).click();
                click(ByConstant.confirm);
            }
        }
        return this;
    }

    public ContactPage updateDepart(String newDepartmentName, String... departmentNames) {
        int i = 0;
        for (String departmentName : departmentNames) {
            LogHelper.info("departmentName:" + departmentName);
            i += 1;
            LogHelper.info("i:" + i);
            if (i < departmentNames.length) {
                By parentDepartmentNameBy = By.linkText(departmentName);
                click(parentDepartmentNameBy);
                ThreadUtil.sleep(300);
                click(parentDepartmentNameBy);
            } else {
                By parentDepartmentNameBy = By.linkText(departmentName);
                click(parentDepartmentNameBy);
                webDriver.findElement(parentDepartmentNameBy)
                        .findElement(By.xpath("span[@class='icon jstree-contextmenu-hover']")).click();
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ByConstant.updateDepartment)).click();
                sendKeys(By.name("name"), newDepartmentName);
                click(ByConstant.save);
            }
        }
        return this;
    }

    public ContactPage addMember(){
        return this;
    }

    public ContactPage updateMember(){
        return this;
    }

    public ContactPage deleteMember(){
        return this;
    }

    public ContactPage searchMember(){
        return this;
    }

    public ContactPage importMember(){
        return this;
    }

    public ContactPage exportMember(){
        return this;
    }

    public String getPartyInfo() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(partyInfo));
        String text = webDriver.findElement(partyInfo).getText();
        LogHelper.info("getPartyInfo: text:" + text);
        return text;
    }

    public String getPartyName() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(partyName));
        String text = webDriver.findElement(partyName).getText();
        LogHelper.info("getPartyName: text:" + text);
        return text;
    }

}
