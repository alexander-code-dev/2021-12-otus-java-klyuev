package ru.otus.annotations.usage;

import java.lang.reflect.InvocationTargetException;

public class AnnotationStarter {
    public static void main(String... args) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {
        AnnotationAnalyzer.init(AnnotationWorkExample.class);
    }
}
