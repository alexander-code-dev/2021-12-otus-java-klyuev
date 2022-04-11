package ru.otus.jdbc.mapper.impl;

import ru.otus.crm.model.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl() {
        Class<T> entity = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        this.name = this.setName(entity);
        this.constructor = this.setConstructor(entity);
        this.idField = this.setIdField(entity);
        this.allFields = this.setAllFields(entity);
        this.fieldsWithoutId = this.setFieldsWithoutId(entity);

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutId;
    }

    private String setName(Class<T> entity) {
        return entity.getSimpleName();
    }

    private Constructor<T> setConstructor(Class<T> entity) {
        Constructor<?>[] allConstructors = entity.getDeclaredConstructors();
        int constructorMaxArgNum = 0;
        int iterLength = 0;
        for (int i =  0; i < allConstructors.length; i++) {
            Class<?>[] pType  = allConstructors[i].getParameterTypes();
            if (iterLength < pType.length) {
                iterLength = pType.length;
                constructorMaxArgNum = i;
            }
        }
        return (Constructor<T>) entity.getDeclaredConstructors()[constructorMaxArgNum];
    }

    private Field setIdField(Class<T> entity) {
        Optional<Field> field = Arrays.stream(entity.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst();
        if (field.isEmpty()) {
            throw new RuntimeException("annotation not found in class");
        } else {
            return field.get();
        }
    }

    private List<Field> setAllFields(Class<T> entity) {
        return Arrays.stream(entity.getDeclaredFields()).toList();
    }

    private List<Field> setFieldsWithoutId(Class<T> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
    }
}
