package ru.otus.jdbc.mapper.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Manager;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntitySQLMetaDataImplTest {

    EntitySQLMetaData entitySQLMetaData;
    EntityClassMetaData<Manager>  entityClassMetaData;

    @BeforeEach
    void init() {
        this.entityClassMetaData = new EntityClassMetaDataImpl<>(){};
        this.entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
    }

    @Test
    void getSelectAllSql() {
        assertEquals("SELECT no, label, param1 FROM Manager",
                entitySQLMetaData.getSelectAllSql());
    }

    @Test
    void getSelectByIdSql() {
        assertEquals("SELECT no, label, param1 FROM Manager t1 WHERE t1.no = ?",
                entitySQLMetaData.getSelectByIdSql());
    }

    @Test
    void getInsertSql() {
        assertEquals("INSERT INTO Manager(label, param1) VALUES (?, ?)",
                entitySQLMetaData.getInsertSql());
    }

    @Test
    void getUpdateSql() {
        assertEquals("UPDATE Manager t1 SET t1.label = ?, t1.param1 = ? WHERE t1.no = ?",
                entitySQLMetaData.getUpdateSql());
    }
}