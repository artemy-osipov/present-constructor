package ru.home.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.domain.repo.PresentRepository;
import ru.home.shop.domain.validator.PresentValidator;
import ru.home.shop.exception.ResourceNotFoundException;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.PresentService;

import java.util.Collection;
import java.util.Map;

@Service
public class PresentServiceImpl implements PresentService {

    private final PresentRepository repository;

    @Autowired
    public PresentServiceImpl(PresentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(PresentBean present) {
        Map<String, String> errors = PresentValidator.validateAdd(present);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Integer newId = repository.add(present);
        present.setId(newId);
    }

    @Override
    public void edit(PresentBean present) {
        Map<String, String> errors = PresentValidator.validateUpdate(present);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        int updated = repository.edit(present);

        if (updated != 1) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public void remove(int id) {
        if (id <= 0) {
            throw new ValidationException("id", "incorrect");
        }

        int removed = repository.remove(id);

        if (removed != 1) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public Collection<PresentBean> listView() {
        return repository.findAll();
    }

    @Override
    public PresentBean find(int id) {
        if (id < 1) {
            throw new ValidationException("id", "incorrect");
        }

        return repository.findFull(id);
    }
}
