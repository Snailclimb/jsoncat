package com.github.jsoncat.common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author shuang.kou
 * @createTime 2020年09月22日 18:07:00
 **/
public class Banner {
    // banner made by https://www.bootschool.net/ascii
    public static final String DEFAULT_BANNER_NAME = "banner.txt";

    public static void printBanner() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(DEFAULT_BANNER_NAME);
        if (url != null) {
            try {
                Path path = Paths.get(url.toURI());
                Files.lines(path).forEach(System.out::println);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }

        }
    }
}
