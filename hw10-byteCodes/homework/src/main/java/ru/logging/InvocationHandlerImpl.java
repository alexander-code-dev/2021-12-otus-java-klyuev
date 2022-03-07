package ru.logging;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class InvocationHandlerImpl implements InvocationHandler {

    private final Object object;
    private final Class<? extends Annotation> annotation;
    private final List<MethodsForInvoke> methodsForInvoke;

    public InvocationHandlerImpl(Object object, Class<? extends Annotation> annotation) {
        this.object = object;
        this.annotation = annotation;
        this.methodsForInvoke = getMethods(object, annotation);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object... args)
            throws Throwable {
        methodsForInvoke.stream()
                .filter(method1 -> method1.getName().equals(method.getName()) && method1.getCountArg() == args.length)
                .forEach(method2 -> System.out.println("executed method: " + method2.getName()
                        + ", param: " + getArgumentStr(args)));
        return method.invoke(object, args);
    }
    /* Возврат строки параметров */
    private String getArgumentStr(Object[] args) {
        StringBuilder argumentStr = new StringBuilder();
        for (int i = 0; args.length > i; i++) {
            argumentStr.append(args[i]);
            if (i+1 < args.length) {
                argumentStr.append(", ");
            }
        }
        return argumentStr.toString();
    }
    /* Массив имен методов с количеством параметров */
    private List<MethodsForInvoke> getMethods(Object object, Class<? extends Annotation> cl) {
        return Arrays.stream(object.getClass().getMethods())
                .filter(f -> f.isAnnotationPresent(cl))
                .map(method -> new MethodsForInvoke(method.getName(), method.getParameters().length))
                .toList();
    }
}
