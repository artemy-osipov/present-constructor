package ru.home.shop.service;

import ru.home.shop.domain.model.Present;

import java.util.Collection;
import java.util.UUID;

public interface PresentService {

    void add(Present present);
    void remove(UUID id);
    Collection<Present> listView();
    Present find(UUID id);
}
