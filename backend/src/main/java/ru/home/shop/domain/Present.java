package ru.home.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Present {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    private String name;
    private BigDecimal price;
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name = "present_item")
    @MapKeyJoinColumn(name = "candy_id")
    @Column(name = "count")
    private Map<Candy, Integer> items = new HashMap<>();

    public BigDecimal computeCost() {
        return items.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
