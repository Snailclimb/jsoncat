package com.github.jsoncat.scanners;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 15:07:00
 **/
@Slf4j
public class AnnotatedClassScanner {

    public Set<Class<?>> scan(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        log.info("The number of class Annotated with  @RestController :[{}]", annotatedClass.size());
        return annotatedClass;
    }
}
