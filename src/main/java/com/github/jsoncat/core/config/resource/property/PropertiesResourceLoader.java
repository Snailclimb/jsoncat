package com.github.jsoncat.core.config.resource.property;

import com.github.jsoncat.core.config.resource.AbstractResourceLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesResourceLoader extends AbstractResourceLoader {

    @Override
    protected Map<String, String> loadResources(Path path) throws IOException {
        Properties properties = new Properties();
        try (InputStream stream = Files.newInputStream(path); Reader reader = new InputStreamReader(stream)) {
            properties.load(reader);
        }
        Map<String, String> resource = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            resource.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return resource;
    }
}
