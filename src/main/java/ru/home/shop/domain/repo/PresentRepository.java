package ru.home.shop.domain.repo;

import ru.home.shop.domain.bean.PresentBean;

import java.util.Collection;

public interface PresentRepository {

    int add(PresentBean present);
    int remove(int id);
    int edit(PresentBean present);
    Collection<PresentBean> findAll();
    PresentBean findFull(int id);
}
