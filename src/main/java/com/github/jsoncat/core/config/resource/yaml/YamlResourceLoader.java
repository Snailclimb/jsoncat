package com.github.jsoncat.core.config.resource.yaml;

import com.github.jsoncat.core.config.resource.AbstractResourceLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlResourceLoader extends AbstractResourceLoader {

    @Override
    protected Map<String, String> loadResources(Path path) throws IOException {

        Map<String, String> result = new LinkedHashMap<>();
        InputStream stream = null;
        Reader reader = null;
        try {
            Yaml yaml = new Yaml();
            stream = Files.newInputStream(path);
            reader = new InputStreamReader(stream);
            Map<String, Object> map = asMap(yaml.load(reader));
            buildFlattenedMap(result, map, null);
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    private void buildFlattenedMap(Map<String, String> result, Map<String, Object> source, String path) {
        source.forEach((key, value) -> {
            if (path != null && !path.isEmpty()) {
                key = path + '.' + key;
            }
            if (value instanceof String) {
                result.put(key, String.valueOf(value));
            } else if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlattenedMap(result, map, key);
            } else {
                result.put(key, (value != null ? String.valueOf(value) : ""));
            }
        });
    }


    private Map<String, Object> asMap(Object object) {
        Map<String, Object> result = new LinkedHashMap<>();

        Map<Object, Object> map = (Map<Object, Object>) object;
        map.forEach((key, value) -> {
            if (value instanceof Map) {
                value = asMap(value);
            }
            result.put(key.toString(), value);
        });
        return result;
    }
}
