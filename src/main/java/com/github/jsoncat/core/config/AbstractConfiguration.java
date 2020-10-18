package com.github.jsoncat.core.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractConfiguration implements Configuration {

    private static Map<String, String> cacheConfigurationMap = new ConcurrentHashMap<>(64);

    @Override
    public int getInt(String id) {
        String result = cacheConfigurationMap.get(id);
        return Integer.parseInt(result);
    }

    @Override
    public String getString(String id) {
        return cacheConfigurationMap.get(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        String result = cacheConfigurationMap.get(id);
        return Boolean.parseBoolean(result);
    }

    @Override
    public void put(String id, String content) {
        cacheConfigurationMap.put(id, content);
    }

    @Override
    public void putAll(Map<String, String> maps) {
        cacheConfigurationMap.putAll(maps);
    }

}
