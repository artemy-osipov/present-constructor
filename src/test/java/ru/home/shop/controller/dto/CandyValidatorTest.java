package ru.home.shop.controller.dto;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class CandyValidatorTest {

    private UpdateCandyDTO getValidDTO() {
        UpdateCandyDTO dto = new UpdateCandyDTO();
        dto.setName("name");
        dto.setFirm("firm");
        dto.setPrice(BigDecimal.valueOf(4.2));
        dto.setOrder(51D);

        return dto;
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
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<UpdateCandyDTO>> constraints = validator.validate(getValidDTO());
        System.out.println(constraints);
        assertTrue(constraints.isEmpty());
    }
}