package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        this.processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        this.checkConfigClass(configClass);
        Object instanceConfigClass = this.getInstanceConfigClass(configClass);
        List<Method> methods = this.getSortedDeclaredMethods(configClass);
        this.executionMethodsInstanceConfigClass(instanceConfigClass, methods);
    }

    private Object getInstanceConfigClass(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Unable to instantiate class %s ", configClass.getSimpleName()), e);
        }
    }

    private List<Method> getSortedDeclaredMethods(Class<?> configClass) {
        return Stream.of(configClass.getDeclaredMethods())
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(AppComponent.class).order()))
                .toList();
    }

    private void executionMethodsInstanceConfigClass(Object instanceConfigClass, List<Method> methods) {
        for (Method method:methods) {
            try {
                Object invokeObject = method.invoke(
                        instanceConfigClass,
                        this.getComponentsForMethodExecutionFromParameters(List.of(method.getParameters())));
                String componentName = method.getAnnotation(AppComponent.class).name();
                if (appComponentsByName.get(componentName) == null) {
                    appComponentsByName.put(componentName, invokeObject);
                    appComponents.add(invokeObject);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(String.format("Failed to execute method %s ", method.getName()), e);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s ", configClass.getName()));
        }
    }

    private Object[] getComponentsForMethodExecutionFromParameters(List<Parameter> parameters) {
        Object[] arrComponent = new Object[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            arrComponent[i] = this.getAppComponent(parameters.get(i).getType());
        }
        return arrComponent;
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object component:appComponents) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                return (C) component;
            }
        }
        throw new RuntimeException("Component not found for "+componentClass.getSimpleName());
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
