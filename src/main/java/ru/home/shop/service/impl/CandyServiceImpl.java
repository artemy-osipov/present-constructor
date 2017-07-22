package ru.home.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.domain.validator.CandyValidator;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.CandyService;

import java.util.Collection;
import java.util.Map;

@Service
public class CandyServiceImpl implements CandyService {

    private final CandyRepository repository;

    @Autowired
    public CandyServiceImpl(CandyRepository repository) {
        this.repository = repository;
    }


    @Override
    public void add(Candy candy) throws ValidationException {
        Map<String, String> errors = CandyValidator.validateAdd(candy);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Integer newId = repository.add(candy);
        candy.setId(newId);
    }

    @Override
    public void edit(Candy candy) throws ValidationException {
        Map<String, String> errors = CandyValidator.validateUpdate(candy);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        int updated = repository.edit(candy);

        if (updated != 1) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void remove(int id) throws ValidationException {
        if (id < 1) {
            throw new ValidationException("id", "incorrect");
        }

        int removed = repository.remove(id);

        if (removed != 1) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Candy> list() {
        return repository.findAll();
    }

    @Override
    public Candy find(int id) throws ValidationException {
        if (id < 1) {
            throw new ValidationException("id", "incorrect");
        }

        return repository.find(id);
    }
}
