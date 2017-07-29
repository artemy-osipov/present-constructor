package ru.home.shop.domain.repo;

import ru.home.shop.domain.model.Candy;

import java.util.Collection;
import java.util.UUID;

public interface CandyRepository {

    void add(Candy candy);
    int remove(UUID id);
    int edit(Candy candy);
    Collection<Candy> list();
    Candy findById(UUID id);
}
