package io.github.artemy.osipov.shop.service.present;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class CreatePresentCommand {
    UUID id;
    String name;
    BigDecimal price;
    List<PresentItem> items;

    @Value
    @Builder
    public static class PresentItem {
        UUID candyId;
        Integer count;
    }
}
