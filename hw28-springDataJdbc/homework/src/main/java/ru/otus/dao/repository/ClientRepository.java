package ru.otus.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.dao.entity.Address;
import ru.otus.dao.entity.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Override
    List<Client> findAll();
}
