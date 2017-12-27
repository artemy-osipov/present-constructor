package ru.home.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "present_id", nullable = false, updatable = false)
    private List<PresentItem> items = new ArrayList<>();

    public BigDecimal computeCost() {
        return getItems().stream()
                .map(item -> item.getCandy().getPrice().multiply(BigDecimal.valueOf(item.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
