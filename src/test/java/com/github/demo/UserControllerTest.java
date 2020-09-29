package com.github.demo;

import com.github.jsoncat.serialize.impl.JacksonSerializer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;


class UserControllerTest {
    private static JacksonSerializer jacksonSerializer;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = TestConfig.host;
        jacksonSerializer = new JacksonSerializer();
    }

    // test @PathVariable
    @Test
    void should_get_user_by_id() {
        when().get("/user/{id}", 1).
                then().
                statusCode(200).
                body("name", equalTo("盖伦"));

    }

    // test @RequestParam
    @Test
    void should_get_user() {
        when().get("/user?name=yasuo&des=哈撒尅&age=18").
                then().
                statusCode(200).
                body("name", equalTo("yasuo"),
                        "des", equalTo("哈撒尅"),
                        "age", equalTo(18));

    }

    //test RequestBody
    @Test
    void should_create_get_successful() {
        UserDto user = new UserDto("压缩", "哈撒尅", 18);
        with().body(jacksonSerializer.serialize(user)).header("Content-Type", "application/json")
                .when().post(TestConfig.host + "/user").
                then().
                statusCode(200);

    }
}