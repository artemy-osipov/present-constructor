package io.github.artemy.osipov.shop.service.candy;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Document("candies")
public class Candy {

    @Id
    private UUID id;
    private String name;
    private String firm;
    private BigDecimal price;
    private Double order;
    private Boolean active = true;

    public void hide() {
        active = false;
    }
}
