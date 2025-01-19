package net.replaceitem.reconfigure.config;

import net.replaceitem.reconfigure.api.ValidationResult;
import net.replaceitem.reconfigure.api.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorListTest {
    @Test
    void testValidation() {
        ValidatorList<String> validatorList = new ValidatorList<>();
        validatorList.add(Validator.ofPredicate(value -> value.length() < 5, "too long"));
        validatorList.add(Validator.ofPredicate(value -> value.contains("o"), "no o"));

        ValidationResult result;
        result = validatorList.validate("good");
        assertTrue(result.isValid());
        assertEquals(result.getMessage(), "");
        
        result = validatorList.validate("way too long");
        assertTrue(result.isInvalid());
        assertEquals(result.getMessage(), "too long");
        
        result = validatorList.validate("very massive");
        assertTrue(result.isInvalid());
        assertEquals(result.getMessage(), "too long");
        
        result = validatorList.validate("miss");
        assertTrue(result.isInvalid());
        assertEquals(result.getMessage(), "no o");
    }
}
