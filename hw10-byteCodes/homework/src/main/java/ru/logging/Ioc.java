package ru.logging;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class Ioc {

    private final Class<?> clazz;
    private final Class<?> interfaces;
    private final Class<? extends Annotation> annotation;
    private static Ioc ioc;

    /* конструктор */
    private Ioc(Class<?> clazz, Class<?> interfaces, Class<? extends Annotation> annotation) {
        this.annotation = annotation;
        this.interfaces = interfaces;
        this.clazz = clazz;
    }
    /* вызвать конструктор если нет ioc */
    public static Ioc createClass(Class<?> clazz, Class<?> interfaces, Class<? extends Annotation> annotation) {
        if (ioc==null) {
            ioc = new Ioc(clazz, interfaces, annotation);
        }
        return ioc;
    }
    /* получить прокси */
    public Object get()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object object = clazz.getDeclaredConstructor().newInstance();
        InvocationHandler handler = new InvocationHandlerImpl(object, annotation);
        return Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{interfaces}, handler);
    }
}
