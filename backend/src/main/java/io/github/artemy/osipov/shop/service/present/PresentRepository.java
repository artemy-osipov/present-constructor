package io.github.artemy.osipov.shop.service.present;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PresentRepository extends MongoRepository<Present, UUID> {
}
