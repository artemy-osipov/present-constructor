package ru.home.shop.service.command.candy;

import org.springframework.data.repository.CrudRepository;
import ru.home.shop.domain.Candy;

import java.util.UUID;

public interface CandyRepository extends CrudRepository<Candy, UUID> {
}
