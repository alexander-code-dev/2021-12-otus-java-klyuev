package ru.otus.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.dao.entity.Address;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {
    @Override
    List<Address> findAll();
}
