package ru.home.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.repo.PresentRepository;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.PresentService;

import java.util.Collection;
import java.util.UUID;

@Service
public class PresentServiceImpl implements PresentService {

    private final PresentRepository repository;

    @Autowired
    public PresentServiceImpl(PresentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Present present) {
        repository.add(present);
    }

    @Override
    public void edit(Present present) {
        int updated = repository.edit(present);

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
    public Collection<Present> listView() {
        return repository.findAll();
    }

    @Override
    public Present find(UUID id) {
        return repository.findFull(id);
    }
}
