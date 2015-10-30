package ru.home.shop.service;

import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.exception.ValidationException;

import java.util.Collection;

public interface CandyService {

    void add(CandyBean candy) throws ValidationException;
    void edit(CandyBean candy) throws ValidationException;
    void remove(int id) throws ValidationException;
    Collection<CandyBean> list();
    CandyBean find(int id) throws ValidationException;
}
