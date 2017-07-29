package ru.home.shop.service;

import ru.home.shop.domain.model.Candy;

import java.util.Collection;
import java.util.UUID;

public interface CandyService {

    void add(Candy candy);
    void edit(Candy candy);
    void remove(UUID id);
    Collection<Candy> list();
    Candy find(UUID id);
}
