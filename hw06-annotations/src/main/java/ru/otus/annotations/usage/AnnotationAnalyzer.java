package ru.otus.annotations.usage;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class AnnotationAnalyzer {
    /* запуск метода с аннотацией */
    private int[] runMethod(Object object, Method method) {
        int pass = 0;
        int fail = 0;
        try { method.invoke(object); pass++; }
        catch (Exception e) { fail++; }
        return new int[]{pass, fail};
    }
    /* запуск методов с аннотацией */
    private void runMethods(Object object, List<Method> methods)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method:methods) {
            method.invoke(object);
        }
    }
    /* Массив методов по аннотации */
    private List<Method> getArraysMethod(Object object, Class<? extends Annotation> cl) {
        return Arrays.stream(object.getClass().getMethods())
                .filter(f -> f.isAnnotationPresent(cl)).toList();
    }
    /* запускалка для выполнения для класса */
    public static void init(Class<?> clazz)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
        analyzer.start(clazz);
    }
    /* Приватная запускалка */
    private void start(Class<?> clazz)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Object object = clazz.getDeclaredConstructor().newInstance();
        List<Method> methodsBefore = getArraysMethod(object, Before.class);
        List<Method> methodsTest = getArraysMethod(object, Test.class);
        List<Method> methodsAfter = getArraysMethod(object, After.class);
        int pass = 0;
        int fail = 0;

        for (Method method : methodsTest) {
            object = clazz.getDeclaredConstructor().newInstance();
            runMethods(object, methodsBefore);
            int[] statisticMethods = runMethod(object, method);
            pass += statisticMethods[0];
            fail += statisticMethods[1];
            runMethods(object, methodsAfter);
        }
        System.out.println("=============");
        System.out.println("Fail test: " + fail);
        System.out.println("Pass test: " + pass);
        int total = fail + pass;
        System.out.println("Total test: " + total);
    }
}
