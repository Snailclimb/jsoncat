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
        InputStream stream = null;
        Reader reader = null;
        try {
            stream = Files.newInputStream(path);
            reader = new InputStreamReader(stream);
            properties.load(reader);

        } finally {
            if (stream != null) {
                stream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        Map<String, String> resource = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            resource.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return resource;
    }
}
