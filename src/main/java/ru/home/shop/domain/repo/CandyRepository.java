package ru.home.shop.domain.repo;

import ru.home.shop.domain.model.Candy;

import java.util.Collection;

public interface CandyRepository {

    int add(Candy candy);
    int remove(int id);
    int edit(Candy candy);
    Collection<Candy> findAll();
    Candy find(int id);
}
