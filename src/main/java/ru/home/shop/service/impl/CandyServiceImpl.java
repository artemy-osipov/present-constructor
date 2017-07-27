package ru.home.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.CandyService;

import java.util.Collection;
import java.util.UUID;

@Service
public class CandyServiceImpl implements CandyService {

    private final CandyRepository repository;

    @Autowired
    public CandyServiceImpl(CandyRepository repository) {
        this.repository = repository;
    }


    @Override
    public void add(Candy candy) {
        repository.add(candy);
    }

    @Override
    public void edit(Candy candy) {
        int updated = repository.edit(candy);

        if (updated == 0) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void remove(UUID id) {
        int removed = repository.remove(id);

        if (removed == 0) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Candy> list() {
        return repository.findAll();
    }

    @Override
    public Candy find(UUID id) {
        return repository.find(id);
    }
}
