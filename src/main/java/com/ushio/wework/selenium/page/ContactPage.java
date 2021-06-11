package com.ushio.wework.selenium.page;

import com.ushio.wework.selenium.ByLocator;
import com.ushio.wework.selenium.bean.MemberBean;
import com.ushio.util.LogHelper;
import com.ushio.util.ThreadUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author: ushio
 * @description:
 **/
public class ContactPage extends BasePage {

    private By partyInfo = ByLocator.cssSelector.js_party_info;
    private By partyName = ByLocator.id.party_name;

    public ContactPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public ContactPage addDepart(String departmentName, String parentDepartmentName) {
        click(ByLocator.linkText.add);
        click(ByLocator.linkText.addDepartment);
        sendKeys(By.name("name"), departmentName);
        click(ByLocator.linkText.chooseDepartment);
        //避免使用滚动方法，可能会有各种问题
        webDriver.findElement(By.tagName("form")).findElement(By.linkText(parentDepartmentName)).click();
        click(ByLocator.linkText.confirm);
        return this;
    }

    public ContactPage addSubDepart(String... departmentNames) {
        int i = 0;
        for (String departmentName : departmentNames) {
            LogHelper.info("departmentName:" + departmentName);
            if (departmentNames.length == 1) {
                //单独处理
                webDriver.findElements(ByLocator.cssSelector.spanIconJstreeContextmenuHover).get(0).click();
                click(ByLocator.linkText.addSubDepartment);
                sendKeys(ByLocator.name.name, departmentName);
                click(ByLocator.linkText.confirm);
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
                        .findElement(ByLocator.xpath.spanIconJstreeContextmenuHover).click();
            } else {
                click(ByLocator.linkText.addSubDepartment);
                sendKeys(ByLocator.name.name, departmentName);
                click(ByLocator.linkText.confirm);
            }
        }
        return this;
    }

    public ContactPage searchDepart(String departmentName) {
        click(ByLocator.id.memberSearchInput);
        sendKeys(ByLocator.id.memberSearchInput, departmentName);
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
                        .findElement(ByLocator.xpath.spanIconJstreeContextmenuHover).click();
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ByLocator.linkText.deleteSubDepartment)).click();
                click(ByLocator.linkText.confirm);
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
                        .findElement(ByLocator.xpath.spanIconJstreeContextmenuHover).click();
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ByLocator.linkText.updateDepartment)).click();
                sendKeys(ByLocator.name.name, newDepartmentName);
                click(ByLocator.linkText.save);
            }
        }
        return this;
    }

    public ContactPage addMember(MemberBean memberBean){
        sendKeys(ByLocator.name.username, memberBean.getName());
        sendKeys(ByLocator.name.english_name, memberBean.getAliasName());
        sendKeys(ByLocator.name.acctid, memberBean.getAccount());
        sendKeys(ByLocator.name.gender, memberBean.getSex());
        sendKeys(ByLocator.name.mobile, memberBean.getMobile());
        sendKeys(ByLocator.name.ext_tel, memberBean.getLandline());
        sendKeys(ByLocator.name.alias, memberBean.getEmail());
        sendKeys(ByLocator.name.xcx_corp_address, memberBean.getAddress());
        //部门
        //标签
        sendKeys(ByLocator.name.position, memberBean.getJob());
        sendKeys(ByLocator.name.identity_stat, memberBean.getIdentity());
        sendKeys(ByLocator.name.extern_position_set, memberBean.getOuterJob());
        if (!memberBean.isInvitated()) {
            click(ByLocator.name.sendInvite);
        }
        click(ByLocator.linkText.save);
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
