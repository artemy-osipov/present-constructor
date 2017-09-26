package ru.home.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.query.candy.CandyEntryRepository;
import ru.home.shop.query.candy.CandyEntry;
import ru.home.shop.service.CandyService;

import java.util.Collection;
import java.util.UUID;

@Service
public class CandyServiceImpl implements CandyService {

    private final CandyEntryRepository repository;

    @Autowired
    public CandyServiceImpl(CandyEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<CandyEntry> list() {
        return repository.list();
    }

    @Override
    public CandyEntry find(UUID id) {
        return repository.findById(id);
    }
}
