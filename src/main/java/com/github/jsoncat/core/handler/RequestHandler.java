package com.github.jsoncat.core.handler;

import io.netty.handler.codec.http.FullHttpRequest;

public interface RequestHandler {
   Object handle(FullHttpRequest fullHttpRequest);
}
