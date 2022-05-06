package ru.otus.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private static final Logger log = LoggerFactory.getLogger(AppComponentsContainerImpl.class);
    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object instanceConfigClass;
        try {
            instanceConfigClass = configClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Unable to instantiate class %s", configClass.getSimpleName()));
        }
        // сортировка
        List<Method> methods = Stream.of(configClass.getDeclaredMethods())
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(AppComponent.class).order()))
                .toList();
        // выполнение методов
        for (Method method:methods) {
            try {
                Object invokeObject;
                if (method.getParameters().length != 0) {
                    invokeObject = method.invoke(instanceConfigClass,
                            this.getAppComponentFromTheList(List.of(method.getParameters())));
                }
                else {
                    invokeObject = method.invoke(instanceConfigClass);
                }
                // заполняем коллекции
                appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), invokeObject);
                appComponents.add(invokeObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(String.format("Failed to execute method %s on class %s",
                        method.getName(),
                        configClass.getSimpleName()));
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    // помощник, его задача отдать простой массив с компонентами для подстановки в параметры метода согласно их порядку
    private Object[] getAppComponentFromTheList(List<Parameter> parameters) {
        Object[] arrComponent = new Object[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            arrComponent[i] = this.getAppComponent(parameters.get(i).getType());
        }
        return arrComponent;
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object component:appComponents) {
            // параметром приехал интерфейс, отдаем первый компонент который его реализует
            if (componentClass.isInterface()) {
                for (Class<?> interfaze:List.of(component.getClass().getInterfaces())) {
                    if (interfaze.getSimpleName().equals(componentClass.getSimpleName())) {
                        return (C) component;
                    }
                }
            }
            // параметром приехал класс, отдаем компонент по реализующему классу
            else if (componentClass.getSimpleName().equals(component.getClass().getSimpleName())) {
                return (C) component;
            }
        }
        log.warn("component not found for {}", componentClass.getSimpleName());
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        // отдаем компонент по имени
        return (C) appComponentsByName.get(componentName);
    }
}
