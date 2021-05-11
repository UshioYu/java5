package com.ushio.wework.selenium.bean;

import lombok.Data;

/**
 * @author: ushio
 * @description:
 **/
@Data
public class MemberBean {
    private String name;
    private String aliasName;
    private String account;
    private String sex = "1";//1 男 2女
    private String mobile;
    private String landline;
    private String email;
    private String address;
    private String department;
    private String label;//预留标签字段
    private String job;
    private String identity = "0";//0 普通成员  1 上级
    private String outerJob = "sync";//sync 同步公司内职务 custom 自定义
    private boolean isInvitated = true;//通过邮件或短信发送企业邀请
}
