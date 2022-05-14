package ru.otus.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.entity.Phone;
import ru.otus.dao.repository.PhoneRepository;
import ru.otus.dao.service.DBService;

import java.util.List;
import java.util.Optional;

@Service
public class DBServicePhoneImpl implements DBService<Phone> {

    private final PhoneRepository phoneRepository;

    public DBServicePhoneImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public Phone saveEntity(Phone entity) {
        return phoneRepository.save(entity);
    }

    @Override
    public Optional<Phone> findById(long id) {
        return phoneRepository.findById(id);
    }

    @Override
    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }
}
