package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        StringJoiner joiner = new StringJoiner(", ");
        //entityClassMetaData.getAllFields().forEach(f -> joiner.add(f.getName()));

        for (Field field:entityClassMetaData.getAllFields()) {
            joiner.add(field.getName());
        }

        return "SELECT "
                .concat(joiner.toString())
                .concat(" FROM ")
                .concat(entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        StringJoiner joiner = new StringJoiner(", ");
        //entityClassMetaData.getAllFields().forEach(f -> joiner.add(f.getName()));

        for (Field field:entityClassMetaData.getAllFields()) {
            joiner.add(field.getName());
        }

        return "SELECT "
                .concat(joiner.toString())
                .concat(" FROM ")
                .concat(entityClassMetaData.getName())
                .concat(" t1 WHERE t1.")
                .concat(entityClassMetaData.getIdField().getName())
                .concat(" = ?");
    }

    @Override
    public String getInsertSql() {
        StringJoiner joinerFieldsName = new StringJoiner(", ");
        StringJoiner joinerFields = new StringJoiner(", ");

        for (Field field:entityClassMetaData.getFieldsWithoutId()) {
            joinerFieldsName.add(field.getName());
            joinerFields.add("?");
        }

        return "INSERT INTO "
                .concat(entityClassMetaData.getName())
                .concat("(")
                .concat(joinerFieldsName.toString())
                .concat(") VALUES (")
                .concat(joinerFields.toString())
                .concat(")");
    }

    @Override
    public String getUpdateSql() {
        StringJoiner joiner = new StringJoiner(", ");
        //entityClassMetaData.getFieldsWithoutId().forEach(f -> joiner.add("t1.".concat(f.getName()).concat(" = ?")));

        for (Field field:entityClassMetaData.getFieldsWithoutId()) {
            joiner.add("t1.".concat(field.getName()).concat(" = ?"));
        }

        return "UPDATE "
                .concat(entityClassMetaData.getName())
                .concat(" t1 SET ")
                .concat(joiner.toString())
                .concat(" WHERE t1.")
                .concat(entityClassMetaData.getIdField().getName())
                .concat(" = ?");
    }
}
