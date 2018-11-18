package ru.home.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    @CollectionTable(name = "present_item", joinColumns = @JoinColumn(name = "present_id"))
    private List<Item> items = new ArrayList<>();

    public BigDecimal computeCost() {
        return items.stream()
                .map(item -> item.getCandy().getPrice().multiply(BigDecimal.valueOf(item.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
