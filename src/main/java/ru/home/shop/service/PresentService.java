package ru.home.shop.service;

import ru.home.shop.domain.bean.PresentBean;

import java.util.Collection;

public interface PresentService {

    void add(PresentBean present);
    void edit(PresentBean present);
    void remove(int id);
    Collection<PresentBean> listView();
    PresentBean find(int id);
}
