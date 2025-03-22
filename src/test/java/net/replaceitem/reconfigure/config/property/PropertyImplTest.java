package net.replaceitem.reconfigure.config.property;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.Validator;
import net.replaceitem.reconfigure.config.ValidatorList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyImplTest {
    private final Identifier ID = Identifier.of("test", "dummy");
    
    static ValidatorList<String> shortStringValidationList() {
        ValidatorList<String> validatorList = new ValidatorList<>();
        validatorList.add(Validator.ofPredicate(s -> s.length() < 5, Text.literal("msg")));
        return validatorList;
    }
    
    @Test
    void testSetResetGet() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "def", new ValidatorList<>());
        assertEquals(property.get(), "def");
        property.set("set");
        assertEquals(property.get(), "set");
        property.reset();
        assertEquals(property.get(), "def");
    }
    
    @Test
    void testInvalidSet() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "", shortStringValidationList());
        assertDoesNotThrow(() -> property.set("shrt"), "Expected no error to be thrown when setting a value satisfies the validators");
        assertEquals(property.get(), "shrt");
        assertThrows(IllegalArgumentException.class, () -> property.set("too long"), "Expected an error when setting a invalid value");
        assertEquals(property.get(), "shrt");
    }
    
    @Test
    void testInvalidSetOrDefault() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "def", shortStringValidationList());
        property.setOrDefault("shrt");
        assertEquals(property.get(), "shrt");
        property.setOrDefault("too long");
        assertEquals(property.get(), "def");
    }
    
    @Test
    void testInvalidSetIfValid() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "def", shortStringValidationList());
        property.setIfValid("shrt");
        assertEquals(property.get(), "shrt");
        property.setIfValid("too long");
        assertEquals(property.get(), "shrt");
    }
    
    @Test
    void testInvalidDefault() {
        assertDoesNotThrow(() -> new PropertyImpl<>(ID, "shrt", shortStringValidationList()), "Expected no error to be thrown when the default value satisfies the validators");
        assertThrows(IllegalArgumentException.class, () -> new PropertyImpl<>(ID, "too long", shortStringValidationList()), "Expected an error when using an invalid default value");
    }
}
