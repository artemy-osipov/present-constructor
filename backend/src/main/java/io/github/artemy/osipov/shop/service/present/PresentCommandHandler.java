package io.github.artemy.osipov.shop.service.present;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PresentCommandHandler {

    private final PresentRepository presentRepository;

    public void on(CreatePresentCommand event) {
        Present present = new Present();
        present.setId(event.getId());
        present.setName(event.getName());
        present.setPrice(event.getPrice());
        present.setDate(LocalDateTime.now());

        event.getItems().forEach(i -> present.addItem(i.getCandyId(), i.getCount()));

        presentRepository.save(present);
    }

    public void on(RemovePresentCommand event) {
        presentRepository.deleteById(event.getId());
    }
}
