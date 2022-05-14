package ru.otus.dao.service;

import java.util.List;
import java.util.Optional;

public interface DBService<T> {
    T saveEntity(T entity);
    Optional<T> findById(long id);
    List<T> findAll();
}
