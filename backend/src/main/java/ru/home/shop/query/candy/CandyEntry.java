package ru.home.shop.query.candy;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "candy")
public class CandyEntry {

    @Id
    @Type(type = "uuid-char")
    private UUID id;
    private String name;
    private String firm;
    private BigDecimal price;
    private Double order;
    private Boolean active = true;
}
