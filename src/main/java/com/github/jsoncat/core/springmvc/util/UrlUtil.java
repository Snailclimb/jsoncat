package com.github.jsoncat.core.springmvc.util;

import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

/**
 * @author shuang.kou
 * @createTime 2020年09月25日 16:00:00
 **/
public class UrlUtil {

    /**
     * get the decoded path of uri
     */
    public static String getRequestPath(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
        return queryDecoder.path();
    }

}
