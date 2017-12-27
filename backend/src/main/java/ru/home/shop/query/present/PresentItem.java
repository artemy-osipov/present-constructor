package ru.home.shop.query.present;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.home.shop.query.candy.CandyEntry;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "present_item")
public class PresentItem {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candy_id")
    private CandyEntry candy;

    private Integer count;
}
