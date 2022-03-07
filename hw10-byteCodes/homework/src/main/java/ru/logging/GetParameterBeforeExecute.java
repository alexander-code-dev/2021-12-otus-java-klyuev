package ru.logging;

import java.lang.reflect.InvocationTargetException;

public class GetParameterBeforeExecute {

    public static void main(String... args)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestLogging testLogging = (TestLogging) Ioc.createClass(TestLoggingImpl.class, TestLogging.class, Log.class).get();
        testLogging.calculation(6);
        testLogging.calculation(6, 6);
        testLogging.calculation(6, 6, "Six");
    }
}
