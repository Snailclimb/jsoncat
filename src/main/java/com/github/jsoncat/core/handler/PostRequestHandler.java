package com.github.jsoncat.core.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import java.util.Date;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 13:33:00
 **/
@Slf4j
public class PostRequestHandler implements RequestHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String typeStr = fullHttpRequest.headers().get("Content-Type");
        String[] list = typeStr.split(";");
        String contentType = list[0];
        Date obj = null;
        if (contentType.equals("application/json")) {
            String jsonStr = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
            try {
                obj = objectMapper.readValue(jsonStr, Date.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}


