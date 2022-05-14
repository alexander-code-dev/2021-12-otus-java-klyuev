package ru.otus.dao.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.entity.Address;
import ru.otus.dao.service.DBService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DataJdbcTest
@Import(DBServiceAddressImpl.class)
@DisplayName("Test address repository")
class DBServiceAddressImplTest {

    @Autowired
    private DBService<Address> dbService;

    @Test
    @DisplayName("update address")
    void updateEntity() {
        Address address = new Address(1L, "Восточная", false);
        dbService.saveEntity(address);
        Optional<Address> optionalAddress = dbService.findById(1);
        if (optionalAddress.isPresent()) {
            assertEquals(optionalAddress.get().getStreet(), "Восточная");
        } else {
            fail("address is empty");
        }
    }

    @Test
    @DisplayName("get address")
    void findById() {
        Optional<Address> optionalAddress = dbService.findById(1);
        if (optionalAddress.isPresent()) {
            assertEquals(optionalAddress.get().getStreet(), "Lenina");
        } else {
            fail("address is empty");
        }
    }

    @Test
    @DisplayName("get all addresses")
    void findAll() {
        List<Address> addresses = dbService.findAll();
        assertEquals(addresses.size(), 2);
    }
}