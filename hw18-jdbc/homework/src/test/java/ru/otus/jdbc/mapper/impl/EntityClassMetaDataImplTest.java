package ru.otus.jdbc.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityClassMetaDataImplTest {
    EntityClassMetaData<Manager> entityClassMetaData;

    @BeforeEach
    void init() {
        this.entityClassMetaData = new EntityClassMetaDataImpl<>(){};
    }

    @Test
    void getName() {
        assertEquals("Manager", entityClassMetaData.getName());
    }

    @Test
    void getConstructorManager()
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Manager manager1 = new Manager();
        manager1.setNo(15L);
        manager1.setLabel("Label");
        manager1.setParam1("Param1");

        Constructor<Manager> constructor = entityClassMetaData.getConstructor();

        long l = 15L;
        String str1 = "Label";
        String str2 = "Param1";

        Object[] object = new Object[]{l, str1, str2};

        Manager manager2 = constructor.newInstance(object);
        assertEquals(manager1, manager2);
    }

    @Test
    void getConstructorClient()
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Client client1 = new Client();
        client1.setId(14L);
        client1.setName("L");

        Constructor<Client> constructor = new EntityClassMetaDataImpl<Client>(){}.getConstructor();
        Long l = 14L;
        String str1 = "L";
        Object[] object = new Object[]{l, str1};

        Client client2 = constructor.newInstance(object);
        assertEquals(client1, client2);
    }

    @Test
    void getIdField() {
        assertEquals( "no", entityClassMetaData.getIdField().getName());
    }

    @Test
    void getAllFields() {
        assertEquals( 3, entityClassMetaData.getAllFields().size());
    }

    @Test
    void getFieldsWithoutId() {
        assertEquals( 2, entityClassMetaData.getFieldsWithoutId().size());
    }
}