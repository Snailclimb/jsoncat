package com.github.jsoncat.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jsoncat.common.util.UrlUtil;
import com.github.jsoncat.core.handler.RequestHandler;
import com.github.jsoncat.core.handler.RequestHandlerFactory;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shuang.kou
 * @createTime 2020年09月23日 17:33:00
 **/
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws JsonProcessingException {
        log.info("Handle http request:{}", fullHttpRequest);
        String uri = fullHttpRequest.uri();
        if (uri.equals(FAVICON_ICO)) {
            return;
        }
        RequestHandler requestHandler = RequestHandlerFactory.create(fullHttpRequest.method());
        Object result;
        FullHttpResponse response;
        try {
            result = requestHandler.handle(fullHttpRequest);
            response = HttpResponse.ok(result);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String requestPath = UrlUtil.getRequestPath(fullHttpRequest.uri());
            response = HttpResponse.internalServerError(requestPath, e.toString());
        }
        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


}
