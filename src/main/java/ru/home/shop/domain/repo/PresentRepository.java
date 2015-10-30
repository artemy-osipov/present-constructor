package ru.home.shop.domain.repo;

import ru.home.shop.domain.bean.PresentBean;

import java.util.Collection;

public interface PresentRepository {

    int addFull(PresentBean present);
    int remove(int id);
    int editFull(PresentBean present);
    Collection<PresentBean> findAll();
    PresentBean findFull(int id);
}
