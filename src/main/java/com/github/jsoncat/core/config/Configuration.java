package com.github.jsoncat.core.config;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface Configuration {

    String APPLICATION_NAME = "application";

    int getInt(String id);

    String getString(String id);

    Boolean getBoolean(String id);

    default void put(String id, String content) {
    }

    default void putAll(Map<String, String> maps) {
    }

    default void loadResources(List<Path> resourcePaths) {
    }
}
