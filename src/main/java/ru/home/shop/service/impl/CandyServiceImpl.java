package ru.home.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.domain.validator.CandyValidator;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.CandyService;

import java.util.Collection;
import java.util.Map;

@Service
public class CandyServiceImpl implements CandyService {

    private CandyRepository repository;

    @Autowired
    public CandyServiceImpl(CandyRepository repository) {
        this.repository = repository;
    }


    @Override
    public void add(CandyBean candy) throws ValidationException {
        Map<String, String> errors = CandyValidator.validateAdd(candy);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        repository.add(candy);
    }

    @Override
    public void edit(CandyBean candy) throws ValidationException {
        Map<String, String> errors = CandyValidator.validateUpdate(candy);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        repository.edit(candy);
    }

    @Override
    public void remove(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException("id", "incorrect");
        }

        repository.remove(id);
    }

    @Override
    public Collection<CandyBean> list() {
        return repository.findAll();
    }

    @Override
    public CandyBean find(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException("id", "incorrect");
        }

        return repository.find(id);
    }
}
