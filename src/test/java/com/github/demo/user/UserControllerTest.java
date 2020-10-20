package com.github.demo.user;

import com.github.demo.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;


class UserControllerTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = TestConfig.host;
    }

    // test @PathVariable
    @Test
    void get_user_by_id() {
        when().get("/user/{id}", 1).
                then().
                statusCode(200).
                body("name", equalTo("盖伦"));

    }

    // test @RequestParam
    @Test
    void get_user() {
        when().get("/user?name=yasuo&des=哈撒尅&age=18").
                then().
                statusCode(200).
                body("name", equalTo("yasuo"),
                        "des", equalTo("哈撒尅"),
                        "age", equalTo(18));

        when().get("/user?name=yasuo&des=哈撒尅").
                then().
                statusCode(500);

    }

    //test @RequestBody
    @Test
    void create_user() {
        UserDto user = new UserDto("压缩", "哈撒尅", 18);
        with().body(user).header("Content-Type", "application/json")
                .when().post("/user").
                then().
                statusCode(200);

    }
}