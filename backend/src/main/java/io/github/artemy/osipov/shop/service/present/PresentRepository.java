package io.github.artemy.osipov.shop.service.present;

import io.github.artemy.osipov.shop.exception.EntityNotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PresentRepository extends MongoRepository<Present, UUID> {

    default Present getById(UUID id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Present.class, id));
    }
}
