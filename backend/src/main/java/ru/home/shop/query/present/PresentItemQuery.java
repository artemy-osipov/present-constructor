package ru.home.shop.query.present;

import lombok.Data;
import ru.home.shop.query.candy.CandyQuery;

@Data
public class PresentItemQuery {

    CandyQuery candy;
    Integer count;
}
