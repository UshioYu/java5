package com.ushio.demo.test.restassured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;


public class RestAssuredTest {

    @Test
    void test1(){
        get("http://127.0.0.1:8089/lotto").then().body("lotto.lottoId",equalTo(5));
        //get("http://127.0.0.1:8089/lotto").then().assertThat().body("lotto.lottoId", equalTo(5));
    }

}
