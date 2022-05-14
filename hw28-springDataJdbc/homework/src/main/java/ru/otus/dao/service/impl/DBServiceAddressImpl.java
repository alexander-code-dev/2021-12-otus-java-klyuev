package ru.otus.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.entity.Address;
import ru.otus.dao.repository.AddressRepository;
import ru.otus.dao.service.DBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBServiceAddressImpl implements DBService<Address> {

    private final AddressRepository addressRepository;

    public DBServiceAddressImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address saveEntity(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> findById(long id) {
        return addressRepository.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return new ArrayList<>(addressRepository.findAll());
    }
}
