package com.ushio.appium.framework.po;

import lombok.Data;

@Data
public class AssertModel {

    private String actual;
    private String matcher;
    private String expect;
    private String reason;
}
