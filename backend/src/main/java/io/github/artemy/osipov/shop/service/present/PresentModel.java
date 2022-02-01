package io.github.artemy.osipov.shop.service.present;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document("presents")
public class PresentModel {

    @Id
    private UUID id;
    private String name;
    private BigDecimal price;
    private LocalDateTime date;
    private List<Item> items = new ArrayList<>();

    public void addItem(UUID candyId, int count) {
        Item item = new Item();
        item.setCandyId(candyId);
        item.setCount(count);
        items.add(item);
    }

    @Data
    public static class Item {
        private UUID candyId;
        private Integer count;
    }
}
