package ru.home.shop.service.present;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.candy.CandyModel;
import ru.home.shop.service.candy.CandyRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PresentService {

    private final PresentRepository presentRepository;
    private final CandyRepository candyRepository;

    public Present getPresent(UUID id) {
        PresentModel presentModel = presentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PresentModel.class, id));
        Set<UUID> candyIds = presentModel.getItems()
                .stream()
                .map(PresentModel.Item::getCandyId)
                .collect(Collectors.toSet());

        List<CandyModel> candies = Streamable.of(
                candyRepository.findAllById(candyIds)
        )
                .stream()
                .collect(Collectors.toList());
        return new Present(presentModel, candies);
    }
}
