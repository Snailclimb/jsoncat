package com.github.jsoncat.scanners;

import annotation.RestController;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnnotatedClassScannerTest {


    @Test
    void should_scan_the_annotated_class() {
        AnnotatedClassScanner annotatedClassScanner = new AnnotatedClassScanner();
        Set<Class<?>> annotatedClasses = annotatedClassScanner.scan("com.github.jsoncat.scanners", RestController.class);
        assertEquals(1, annotatedClasses.size());
    }

}