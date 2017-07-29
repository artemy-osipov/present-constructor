package ru.home.shop.domain.repo;

import ru.home.shop.domain.model.Present;

import java.util.Collection;
import java.util.UUID;

public interface PresentRepository {

    void add(Present present);
    int remove(UUID id);
    int edit(Present present);
    Collection<Present> listView();
    Present findById(UUID id);
}
