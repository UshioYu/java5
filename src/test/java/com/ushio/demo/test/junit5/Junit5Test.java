package com.ushio.demo.test.junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author: ushio
 * @description:
 **/
public class Junit5Test {

    @ParameterizedTest
    @ValueSource(strings = {"a","b"," ",""})
    void parameterTest(String msg){
        System.out.println(msg);
    }
}
