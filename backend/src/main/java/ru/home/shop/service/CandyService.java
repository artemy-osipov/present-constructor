package ru.home.shop.service;

import ru.home.shop.query.candy.CandyEntry;

import java.util.Collection;
import java.util.UUID;

public interface CandyService {

    Collection<CandyEntry> list();
    CandyEntry find(UUID id);
}
