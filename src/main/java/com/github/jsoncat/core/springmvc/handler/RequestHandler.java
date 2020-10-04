package com.github.jsoncat.core.springmvc.handler;

import io.netty.handler.codec.http.FullHttpRequest;

public interface RequestHandler {
    Object handle(FullHttpRequest fullHttpRequest);
}
