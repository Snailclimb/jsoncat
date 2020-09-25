package com.github.jsoncat.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年09月25日 16:00:00
 **/
public class UrlUtils {

    /**
     * get the parameters of url
     */
    public static Map<String, String> getQueryParams(Map<String, List<String>> uriAttributes) {
        Map<String, String> queryParams = new HashMap<>();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            for (String attrVal : attr.getValue()) {
                queryParams.put(attr.getKey(), attrVal);
            }
        }
        return queryParams;
    }
}
