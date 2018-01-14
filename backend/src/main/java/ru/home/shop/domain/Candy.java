package ru.home.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Candy {

    @Id
    @Type(type = "uuid-char")
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
