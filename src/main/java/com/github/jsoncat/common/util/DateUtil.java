package com.github.jsoncat.common.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author shuang.kou
 * @createTime 2020年09月26日 10:42:00
 **/
public class DateUtil {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.CHINA)
                    .withZone(ZoneId.systemDefault());

    public static String now() {
        return FORMATTER.format(Instant.now());
    }
}
