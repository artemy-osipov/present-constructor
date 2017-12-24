package ru.home.shop.query.present;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PresentEntryRepository extends CrudRepository<PresentEntry, UUID> {
}
