package ru.home.shop.service;

import ru.home.shop.domain.model.Present;

import java.util.Collection;

public interface PresentService {

    void add(Present present);
    void edit(Present present);
    void remove(int id);
    Collection<Present> listView();
    Present find(int id);
}
