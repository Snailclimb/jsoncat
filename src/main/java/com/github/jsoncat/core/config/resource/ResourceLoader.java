package com.github.jsoncat.core.config.resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface ResourceLoader {

    Map<String, String> loadResource(Path path) throws IOException;
}
