package ru.home.shop.domain.validator;

import org.springframework.util.StringUtils;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;

import java.util.HashMap;
import java.util.Map;

public class PresentValidator {

    public static Map<String, String> validate(Present present) {
        if (present == null) {
            throw new IllegalArgumentException("argument is null");
        }

        Map<String, String> errors = new HashMap<>();

        if (StringUtils.isEmpty(present.getName())) {
            errors.put("name", "empty");
        } else if (present.getName().length() > 50) {
            errors.put("name", "length exceeded (50)");
        }

        if (present.getPrice() == null) {
            errors.put("price", "empty");
        } else if (present.getPrice().doubleValue() <= 0 || present.getPrice().scale() > 2) {
            errors.put("price", "incorrect");
        }

        if (present.getCandies().isEmpty()) {
            errors.put("candies", "empty");
        } else {
            present.getCandies().forEach(c -> validateCandy(c, errors));
        }

        return errors;
    }

    private static void validateCandy(Candy candy, Map<String, String> errors) {
        if (candy.getVid() == null || candy.getVid() < 1) {
            errors.put("candies.vid", "incorrect");
        }

        if (candy.getCount() < 1) {
            errors.put("candies.count", "incorrect");
        }
    }
}
