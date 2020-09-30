package com.github.demo.sms;

import com.github.demo.TestConfig;
import com.github.jsoncat.serialize.impl.JacksonSerializer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SmsControllerTest {
    private static JacksonSerializer jacksonSerializer;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = TestConfig.host;
        jacksonSerializer = new JacksonSerializer();
    }

    // test @Qualifier
    @Test
    void should_send_sms_message_successful() {
        Response response = with().body(jacksonSerializer.serialize(new SmsDto("18163138577"))).header("Content-Type", "application/json").
                when().post("sms/send");
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains(AliSmsServiceImpl.class.getSimpleName()));
    }
}