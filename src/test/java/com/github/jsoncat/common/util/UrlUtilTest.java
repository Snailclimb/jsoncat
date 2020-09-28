package com.github.jsoncat.common.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UrlUtilTest {
    @Test
    void should_format_url_successful() {
        String url = "/user/{name}";
        String formatUri = UrlUtil.formatUrl(url);
        Pattern pattern = Pattern.compile(formatUri);
        assertEquals("^/user/[\\u4e00-\\u9fa5_a-zA-Z0-9]+/?$", formatUri);
        boolean a = pattern.matcher("/user/盖伦/1232").find();
        assertFalse(a);
        boolean b = pattern.matcher("/user/盖伦").find();
        assertTrue(b);

    }
}