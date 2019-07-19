package ru.home.shop.service.query.present;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.home.shop.service.query.candy.CandyQuery;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PresentItemQuery {

    CandyQuery candy;
    Integer count;
}
