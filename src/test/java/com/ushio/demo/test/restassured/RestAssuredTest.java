package com.ushio.demo.test.restassured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class RestAssuredTest {

    @Test
    void test1(){
        get("http://127.0.0.1:8089/lotto").then().body("lotto.lottoId",equalTo(5));
        //get("http://127.0.0.1:8089/lotto").then().assertThat().body("lotto.lottoId", equalTo(5));
    }

    @Test
    void test2(){
        Response response = get("http://127.0.0.1:8089/lotto").then().log().body()
                .extract()
                .response();
        String str = response.path("html.body").toString();
        assertEquals(0, JSONObject.parseObject(str).getIntValue("code"));
    }

}
