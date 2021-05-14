package com.ushio.framework.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ushio.framework.model.WebModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.util.stream.Stream;

/**
 * @author: ushio
 * @description:
 **/
public class ParaTest {

    @Test
    public void searchTest(){
        //String text = webDriver.findElements(By.cssSelector(".topic-title")).get(1).getText();
        //assertThat(text,containsString(keywords));
    }

    @ParameterizedTest
    @MethodSource("loadWebModel")
    void testWebModelWithMethodSource(WebModel webModel) {
        webModel.run();
    }

    static Stream<WebModel> loadWebModel() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        WebModel webModel = null;
        try {
            webModel = objectMapper.readValue(new File("src/test/resources/testcase/WebModel.yaml"), WebModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stream.of(webModel);
    }
}
