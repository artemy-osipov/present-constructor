package ru.home.shop.service.candy;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CandyRepository extends MongoRepository<CandyModel, UUID> {
}
