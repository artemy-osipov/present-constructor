package ru.home.shop.query.candy;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity(name = "candy")
public class CandyEntry {

    @Id
    @Type(type = "uuid-char")
    UUID id;
    String name;
    String firm;
    BigDecimal price;
    double order;
    Boolean active = true;
}
