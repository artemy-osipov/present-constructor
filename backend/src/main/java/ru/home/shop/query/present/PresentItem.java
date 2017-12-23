package ru.home.shop.query.present;

import lombok.Data;
import org.hibernate.annotations.Type;
import ru.home.shop.query.candy.CandyEntry;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = "present_item")
public class PresentItem {

    @Id
    @Type(type = "uuid-char")
    UUID id;

    @OneToOne
    @JoinColumn(name = "candy_id")
    CandyEntry candy;

    Integer count;
}
