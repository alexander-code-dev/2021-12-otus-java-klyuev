package ru.otus.dao.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import ru.otus.dao.entity.Address;
import ru.otus.dao.entity.Client;
import ru.otus.dao.entity.Phone;
import ru.otus.dao.service.DBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DataJdbcTest
@Import(DBServiceClientImpl.class)
@DisplayName("Test client repository")
class DBServiceClientImplTest {

    @Autowired
    private DBService<Client> dbService;

    @Test
    @DisplayName("save client")
    void saveEntity() throws InterruptedException {
        Address address = new Address(null, "Восточная", true);
        Phone phone = new Phone(null, "8-111-999-00-15", true);
        Client client = new Client(null, "Сашек", address, Set.of(phone), true);
        dbService.saveEntity(client);
        Optional<Client> optionalClient = dbService.findById(3);
        if (optionalClient.isPresent()) {
            System.out.println(optionalClient.get());
            assertEquals(optionalClient.get().getName(), "Сашек");
            assertEquals(optionalClient.get().getAddress().getStreet(), "Восточная");
            List<Phone> phones = new ArrayList<>(optionalClient.get().getPhones());
            assertEquals(phones.get(0).getNumber(), "8-111-999-00-15");
        } else {
            fail("client is empty");
        }
    }

    @Test
    @DisplayName("update client")
    void updateEntity() {
        Client client = new Client(1L, "Владимир", null, null, false);
        dbService.saveEntity(client);
        Optional<Client> optionalClient = dbService.findById(1);
        if (optionalClient.isPresent()) {
            assertEquals(optionalClient.get().getName(), "Владимир");
        } else {
            fail("client is empty");
        }
    }

    @Test
    @DisplayName("get client")
    void findById() {
        Optional<Client> optionalClient = dbService.findById(1);
        if (optionalClient.isPresent()) {
            assertEquals(optionalClient.get().getName(), "Александр");
        } else {
            fail("client is empty");
        }
    }

    @Test
    @DisplayName("get all client")
    void findAll() {
        List<Client> clients = dbService.findAll();
        assertEquals(clients.size(), 2);
    }
}