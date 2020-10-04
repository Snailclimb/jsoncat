package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.core.springmvc.handler.GetRequestHandler;
import com.github.jsoncat.core.springmvc.handler.PostRequestHandler;
import com.github.jsoncat.core.springmvc.handler.RequestHandler;
import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:28:00
 **/
public class RequestHandlerFactory {
    public static final Map<HttpMethod, RequestHandler> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(HttpMethod.GET, new GetRequestHandler());
        REQUEST_HANDLERS.put(HttpMethod.POST, new PostRequestHandler());
    }

    public static RequestHandler create(HttpMethod httpMethod) {
        return REQUEST_HANDLERS.get(httpMethod);
    }
}
