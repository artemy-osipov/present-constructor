package ru.home.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@Setter
@Entity
public class PresentItem {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candy_id")
    private Candy candy;

    private Integer count;
}
