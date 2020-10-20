package com.github.demo.circularDependency;

import com.github.demo.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;

class CircularDependencyControllerTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = TestConfig.host;
    }

    @Test
    void circular_dependency_test() {
        with().when().get("circular-dependency").then().assertThat().statusCode(200);
    }
}