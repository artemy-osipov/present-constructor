package ru.home.shop.domain.validator;

import org.springframework.util.StringUtils;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.bean.PresentBean;

import java.util.HashMap;
import java.util.Map;

public class PresentValidator {

    public static Map<String, String> validateAdd(PresentBean present) {
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

    private static void validateCandy(CandyBean candy, Map<String, String> errors) {
        if (candy.getId() == null || candy.getId() < 1) {
            errors.put("candies.id", "incorrect");
        }

        if (candy.getCount() < 1) {
            errors.put("candies.count", "incorrect");
        }
    }

    public static Map<String, String> validateUpdate(PresentBean present) {
        Map<String, String> errors = validateAdd(present);

        if (present.getId() == null) {
            errors.put("id", "empty");
        } else if (present.getId() < 1) {
            errors.put("id", "incorrect");
        }

        return errors;
    }
}
