package ru.home.shop.domain.validator;

import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PresentValidatorTest {

    private static final String LONG_STRING = "1234567890abcdefghijklmnopqrstuvwxyz_!@#$%^&*()+|,/";

    private Present getValidAddPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setVid(1);
        candy1.setCount(2);

        Candy candy2 = new Candy();
        candy2.setVid(3);
        candy2.setCount(6);

        present.getCandies().add(candy1);
        present.getCandies().add(candy2);

        return present;
    }

    private Map<String, String> getEmptyErrorsForAdding() {
        Map<String, String> errors = new HashMap<>();
        errors.put("name", "empty");
        errors.put("price", "empty");
        errors.put("candies", "empty");

        return errors;
    }

    private Map<String, String> getIncorrectEmptyErrorsForAdding() {
        Map<String, String> errors = new HashMap<>();
        errors.put("name", "length exceeded (50)");
        errors.put("price", "incorrect");
        errors.put("candies.vid", "incorrect");
        errors.put("candies.count", "incorrect");

        return errors;
    }

    @Test
    public void validateAdd_validEntry_shouldReturnNoErrors() {
        Map<String, String> errors = PresentValidator.validateAdd(getValidAddPresent());

        assertTrue(errors.isEmpty());
    }

    @Test
    public void validateAdd_nullEntry_shouldThrowException() {
        try {
            PresentValidator.validateAdd(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void validateAdd_emptyEntry_shouldReturnFewErrors() {
        assertEquals(getEmptyErrorsForAdding(), PresentValidator.validateAdd(new Present()));
    }

    @Test
    public void validateAdd_notValidEntry_shouldReturnFewErrors() {
        Present present = getValidAddPresent();
        present.setName(LONG_STRING);
        present.setPrice(BigDecimal.valueOf(12.414));

        Candy candy = new Candy();
        candy.setId(-1);
        candy.setCount(0);
        present.getCandies().add(candy);

        assertEquals(getIncorrectEmptyErrorsForAdding(), PresentValidator.validateAdd(present));
    }

    @Test
    public void validateUpdate_validEntry_shouldReturnNoErrors() {
        Present present = getValidAddPresent();
        present.setId(1);

        Map<String, String> errors = PresentValidator.validateUpdate(present);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void validateUpdate_nullEntry_shouldThrowException() {
        try {
            PresentValidator.validateUpdate(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void validateUpdate_emptyEntry_shouldReturnFewErrors() {
        Map<String, String> errors = getEmptyErrorsForAdding();
        errors.put("id", "empty");

        assertEquals(errors, PresentValidator.validateUpdate(new Present()));
    }

    @Test
    public void validateUpdate_notValidEntry_shouldReturnFewErrors() {
        Present present = getValidAddPresent();
        present.setId(-1);
        present.setName(LONG_STRING);
        present.setPrice(BigDecimal.valueOf(0));

        Candy candy = new Candy();
        candy.setVid(-1);
        candy.setCount(0);
        present.getCandies().add(candy);

        Map<String, String> errors = getIncorrectEmptyErrorsForAdding();
        errors.put("id", "incorrect");

        assertEquals(errors, PresentValidator.validateUpdate(present));
    }
}