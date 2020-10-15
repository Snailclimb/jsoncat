package com.github.demo.aop;

import com.github.demo.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentControllerTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = TestConfig.host;
    }

    // test @Qualifier
    @Test
    void should_get_student_summary_successful() {
        Response response = with().when().get("student/summary");
        assertEquals(200, response.getStatusCode());
        System.out.println(response.getBody().asString());
        assertTrue(response.getBody().asString().contains("i am a good student! The teacher said I was great. The HeadMaster said I was very clever."));
    }
}