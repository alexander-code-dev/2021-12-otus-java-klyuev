package ru.otus.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.dao.entity.Address;
import ru.otus.dao.entity.Phone;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    @Override
    List<Phone> findAll();
}
