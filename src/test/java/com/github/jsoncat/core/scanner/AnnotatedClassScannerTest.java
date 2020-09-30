package com.github.jsoncat.core.scanner;

import com.github.jsoncat.annotation.RestController;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnnotatedClassScannerTest {


    @Test
    void should_scan_the_annotated_class() {
        Set<Class<?>> annotatedClasses = AnnotatedClassScanner.scan("com.github.demo", RestController.class);
        assertEquals(2, annotatedClasses.size());
    }

}