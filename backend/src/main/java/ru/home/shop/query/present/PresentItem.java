package ru.home.shop.query.present;

import lombok.Data;
import ru.home.shop.query.candy.CandyEntry;

@Data
public class PresentItem {

    CandyEntry candy;
    Integer count;
}
