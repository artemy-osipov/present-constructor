package ru.home.shop.domain.repo;

import ru.home.shop.domain.bean.CandyBean;

import java.util.Collection;

public interface CandyRepository {

    int add(CandyBean candy);
    int remove(int id);
    int edit(CandyBean candy);
    Collection<CandyBean> findAll();
    CandyBean find(int id);
}
