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
    /* запускалка для выполнения для класса */
    public static void init(Class<?> clazz) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {
        AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
        analyzer.start(clazz);
    }
    /* Приватная запускалка */
    private void start(Class<?> clazz) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {
        Object object = clazz.getDeclaredConstructor().newInstance();
        List<Method> methodsBefore = getArraysMethod(object, Before.class);
        List<Method> methodsTest = getArraysMethod(object, Test.class);
        List<Method> methodsAfter = getArraysMethod(object, After.class);
        int pass = 0;
        int fail = 0;
        boolean failBefore = false;
        boolean failAfter = false;

        for (int i = 0; i < methodsTest.size() && !failBefore && !failAfter; i++) {
            object = clazz.getDeclaredConstructor().newInstance();
            if (runMethods(object, methodsBefore, false)) {
                int[] statisticMethods = runMethod(object, methodsTest.get(i));
                pass += statisticMethods[0];
                fail += statisticMethods[1];
            } else {
                fail = methodsTest.size();
                failBefore = true;
            }
            if (!runMethods(object, methodsAfter, true)) {
                fail = methodsTest.size();
                failAfter = true;
                pass = 0;
            }
        }
        System.out.println("=============");
        System.out.println("Fail: " + fail);
        System.out.println("Pass: " + pass);
        System.out.println("Total: " + methodsTest.size());
    }
    /* запуск метода с аннотацией */
    private int[] runMethod(Object object, Method method) {
        int pass = 0;
        int fail = 0;
        try {
            method.invoke(object);
            pass++;
        } catch (Exception e) {
            fail++;
        }
        return new int[]{pass, fail};
    }
    /* запуск методов с аннотацией */
    private boolean runMethods(Object object, List<Method> methods, boolean afterFlag) {
        int fail = 0;
        for (Method method:methods) {
            try {
                method.invoke(object);
            } catch (Exception e) {
                fail++;
                System.out.println("Tried to execute " + method.getName() + ", but got an error.");
                if (fail > 0 && !afterFlag) {
                    break;
                }
            }
        }
        return fail == 0;
    }
    /* Массив методов по аннотации */
    private List<Method> getArraysMethod(Object object, Class<? extends Annotation> cl) {
        return Arrays.stream(object.getClass().getMethods())
                .filter(f -> f.isAnnotationPresent(cl)).toList();
    }
}
