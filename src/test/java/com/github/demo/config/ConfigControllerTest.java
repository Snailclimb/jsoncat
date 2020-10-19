package com.github.demo.config;

import com.github.demo.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.containsString;

class ConfigControllerTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = TestConfig.host;
    }

    @Test
    void should_get_properties_successfully() {
        String expect = "javaguide";
        with().when().get("config/project-info").then().assertThat().statusCode(200)
                .body(containsString(expect));
        String expect2 = "jsoncat";
        with().when().get("config?key=jsoncat.name").then().assertThat().statusCode(200)
                .body(containsString(expect2));
    }
}