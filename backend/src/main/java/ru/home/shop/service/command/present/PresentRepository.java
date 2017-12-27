package ru.home.shop.service.command.present;

import org.springframework.data.repository.CrudRepository;
import ru.home.shop.domain.Present;

import java.util.UUID;

public interface PresentRepository extends CrudRepository<Present, UUID> {
}
