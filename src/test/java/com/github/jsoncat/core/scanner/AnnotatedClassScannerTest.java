package com.github.jsoncat.core.scanner;

import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.common.util.ReflectionUtil;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnnotatedClassScannerTest {


    @Test
    void should_scan_the_annotated_class() {
        Set<Class<?>> annotatedClasses = ReflectionUtil.scanAnnotatedClass("com.github.demo", RestController.class);
        assertEquals(4, annotatedClasses.size());
    }

}