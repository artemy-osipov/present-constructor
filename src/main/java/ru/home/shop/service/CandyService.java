package ru.home.shop.service;

import ru.home.shop.domain.model.Candy;
import ru.home.shop.exception.ValidationException;

import java.util.Collection;

public interface CandyService {

    void add(Candy candy) throws ValidationException;
    void edit(Candy candy) throws ValidationException;
    void remove(int id) throws ValidationException;
    Collection<Candy> list();
    Candy find(int id) throws ValidationException;
}
