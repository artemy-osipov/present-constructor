package ru.home.shop.api.present;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@AllArgsConstructor
public class CreatePresentCommand {

    private UUID id;
    private String name;
    private BigDecimal price;
    private LocalDateTime date;
    private List<PresentItem> items;


    @Value
    public static class PresentItem {

        private UUID candyId;
        private Integer count;
    }
}
