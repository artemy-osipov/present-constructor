package ru.home.shop.query.present;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PresentEntryJpaRepository extends CrudRepository<PresentEntry, UUID> {
}
