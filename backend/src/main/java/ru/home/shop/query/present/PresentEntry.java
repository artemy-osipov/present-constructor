package ru.home.shop.query.present;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "present")
public class PresentEntry {

    @Id
    @Type(type = "uuid-char")
    UUID id;

    String name;
    BigDecimal price;
    LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "present_id", nullable = false, updatable = false)
    List<PresentItem> items = new ArrayList<>();

    public BigDecimal computeCost() {
        return getItems().stream()
                .map(c -> c.getCandy().getPrice().multiply(BigDecimal.valueOf(c.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
