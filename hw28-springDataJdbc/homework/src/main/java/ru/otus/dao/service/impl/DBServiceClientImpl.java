package ru.otus.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.entity.Address;
import ru.otus.dao.entity.Client;
import ru.otus.dao.repository.AddressRepository;
import ru.otus.dao.repository.ClientRepository;
import ru.otus.dao.service.DBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBServiceClientImpl implements DBService<Client> {

    private final ClientRepository clientRepository;

    public DBServiceClientImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveEntity(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clientRepository.findAll());
    }
}
