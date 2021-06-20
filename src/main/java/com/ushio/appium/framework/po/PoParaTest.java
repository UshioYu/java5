package com.ushio.appium.framework.po;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

public class PoParaTest {

    @ParameterizedTest
    @MethodSource("loadPoTestCase")
    void testPoTestCaseWithMethodSource(PoTestCase poTestCase) {
        poTestCase.run();
    }

    static Stream<PoTestCase> loadPoTestCase() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        PoTestCase poTestCase = null;
        try {
            poTestCase = objectMapper.readValue(new File("src/main/resources/testcase/PoScreenTest.yaml"), PoTestCase.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stream.of(poTestCase);
    }
}
