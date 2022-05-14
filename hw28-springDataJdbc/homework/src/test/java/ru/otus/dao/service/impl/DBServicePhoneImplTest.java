package ru.otus.dao.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.entity.Phone;
import ru.otus.dao.service.DBService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DataJdbcTest
@Import(DBServicePhoneImpl.class)
@DisplayName("Test phone repository")
class DBServicePhoneImplTest {

    @Autowired
    private DBService<Phone> dbService;

    @Test
    @DisplayName("update phone")
    void updateEntity() {
        Phone phone = new Phone(1L, "8-111-999-00-15", false);
        dbService.saveEntity(phone);
        Optional<Phone> optionalPhone = dbService.findById(1);
        if (optionalPhone.isPresent()) {
            assertEquals(optionalPhone.get().getNumber(), "8-111-999-00-15");
        } else {
            fail("phone is empty");
        }
    }

    @Test
    @DisplayName("get phone")
    void findById() {
        Optional<Phone> optionalPhone = dbService.findById(1);
        if (optionalPhone.isPresent()) {
            assertEquals(optionalPhone.get().getNumber(), "555-13-22");
        } else {
            fail("phone is empty");
        }
    }

    @Test
    @DisplayName("get all phone")
    void findAll() {
        List<Phone> phones = dbService.findAll();
        assertEquals(phones.size(), 3);
    }
}