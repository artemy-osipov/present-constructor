package ru.home.shop.domain.validator;

import org.junit.Test;
import ru.home.shop.domain.model.Candy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CandyValidatorTest {

    private static final String LONG_STRING = "1234567890abcdefghijklmnopqrstuvwxyz_!@#$%^&*()+|,/";

    private Candy getValidAddCandy() {
        Candy candy = new Candy();
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.2));
        candy.setOrder(51);

        return candy;
    }

    private Map<String, String> getEmptyErrorsForAdding() {
        Map<String, String> errors = new HashMap<>();
        errors.put("name", "empty");
        errors.put("firm", "empty");
        errors.put("price", "empty");

        return errors;
    }

    private Map<String, String> getIncorrectEmptyErrorsForAdding() {
        Map<String, String> errors = new HashMap<>();
        errors.put("name", "length exceeded (50)");
        errors.put("firm", "length exceeded (50)");
        errors.put("price", "incorrect");
        errors.put("order", "incorrect");

        return errors;
    }

    @Test
    public void validate_validEntry_shouldReturnNoErrors() {
        Map<String, String> errors = CandyValidator.validate(getValidAddCandy());

        assertTrue(errors.isEmpty());
    }

    @Test
    public void validate_nullEntry_shouldThrowException() {
        try {
            CandyValidator.validate(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void validate_emptyEntry_shouldReturnFewErrors() {
        assertEquals(getEmptyErrorsForAdding(), CandyValidator.validate(new Candy()));
    }

    @Test
    public void validate_notValidEntry_shouldReturnFewErrors() {
        Candy candy = getValidAddCandy();
        candy.setName(LONG_STRING);
        candy.setFirm(LONG_STRING);
        candy.setPrice(BigDecimal.valueOf(0));
        candy.setOrder(-24);

        assertEquals(getIncorrectEmptyErrorsForAdding(), CandyValidator.validate(candy));
    }
}