package ru.home.shop.service.command.candy;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.shop.domain.Candy;

import java.util.UUID;

public interface CandyRepository extends JpaRepository<Candy, UUID> {
}
