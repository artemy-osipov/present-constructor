package ru.home.shop.domain.repo;

import ru.home.shop.domain.model.Present;

import java.util.Collection;

public interface PresentRepository {

    int add(Present present);
    int remove(int id);
    int edit(Present present);
    Collection<Present> findAll();
    Present findFull(int id);
}
