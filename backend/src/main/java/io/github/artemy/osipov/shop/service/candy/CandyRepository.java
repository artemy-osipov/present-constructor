package io.github.artemy.osipov.shop.service.candy;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CandyRepository extends MongoRepository<Candy, UUID> {
}
