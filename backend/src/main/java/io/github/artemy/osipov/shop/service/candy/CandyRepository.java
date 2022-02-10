package io.github.artemy.osipov.shop.service.candy;

import io.github.artemy.osipov.shop.exception.EntityNotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CandyRepository extends MongoRepository<Candy, UUID> {

    default Candy getById(UUID id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Candy.class, id));
    }
}
