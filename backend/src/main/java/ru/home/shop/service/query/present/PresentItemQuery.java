package ru.home.shop.service.query.present;

import lombok.Data;
import ru.home.shop.service.query.candy.CandyQuery;

@Data
public class PresentItemQuery {

    CandyQuery candy;
    Integer count;
}
