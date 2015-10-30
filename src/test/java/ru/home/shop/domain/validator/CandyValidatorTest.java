package ru.home.shop.domain.validator;

import org.junit.Test;
import ru.home.shop.domain.bean.CandyBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CandyValidatorTest {

    private static final String LONG_STRING = "1234567890abcdefghijklmnopqrstuvwxyz_!@#$%^&*()+|,/";

    private CandyBean getValidAddCandy() {
        CandyBean candy = new CandyBean();
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
    public void validateAdd_validEntry_shouldReturnNoErrors() {
        Map<String, String> errors = CandyValidator.validateAdd(getValidAddCandy());

        assertTrue(errors.isEmpty());
    }

    @Test
    public void validateAdd_nullEntry_shouldThrowException() {
        try {
            CandyValidator.validateAdd(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void validateAdd_emptyEntry_shouldReturnFewErrors() {
        assertEquals(getEmptyErrorsForAdding(), CandyValidator.validateAdd(new CandyBean()));
    }

    @Test
    public void validateAdd_notValidEntry_shouldReturnFewErrors() {
        CandyBean candy = getValidAddCandy();
        candy.setName(LONG_STRING);
        candy.setFirm(LONG_STRING);
        candy.setPrice(BigDecimal.valueOf(0));
        candy.setOrder(-24);

        assertEquals(getIncorrectEmptyErrorsForAdding(), CandyValidator.validateAdd(candy));
    }

    @Test
    public void validateUpdate_validEntry_shouldReturnNoErrors() {
        CandyBean candy = getValidAddCandy();
        candy.setId(1);

        Map<String, String> errors = CandyValidator.validateUpdate(candy);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void validateUpdate_nullEntry_shouldThrowException() {
        try {
            CandyValidator.validateUpdate(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void validateUpdate_emptyEntry_shouldReturnFewErrors() {
        Map<String, String> errors = getEmptyErrorsForAdding();
        errors.put("id", "empty");

        assertEquals(errors, CandyValidator.validateUpdate(new CandyBean()));
    }

    @Test
    public void validateUpdate_notValidEntry_shouldReturnFewErrors() {
        CandyBean candy = getValidAddCandy();
        candy.setId(-1);
        candy.setName(LONG_STRING);
        candy.setFirm(LONG_STRING);
        candy.setPrice(BigDecimal.valueOf(0));
        candy.setOrder(-24);

        Map<String, String> errors = getIncorrectEmptyErrorsForAdding();
        errors.put("id", "incorrect");

        assertEquals(errors, CandyValidator.validateUpdate(candy));
    }
}