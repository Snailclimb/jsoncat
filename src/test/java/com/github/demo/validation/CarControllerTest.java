package com.github.demo.validation;

import com.github.demo.TestConfig;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;

class CarControllerTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = TestConfig.host;
    }

    @Test
    void should_throw_an_error() {
        CarDto carDto = new CarDto();
        with().body(carDto).header("Content-Type", "application/json")
            .when()
            .post("/cars")
            .then()
            .statusCode(500)
            .body("message", Matchers.equalTo("javax.validation.ConstraintViolationException: name: must not be null"));
    }

    @Test
    void should_request_successfully() {
        CarDto carDto = new CarDto();
        carDto.setName("mycar");
        with().body(carDto).header("Content-Type", "application/json")
                .when()
                .post("/cars")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo("mycar"));
    }
}
