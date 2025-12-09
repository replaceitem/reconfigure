package net.replaceitem.reconfigure.api;

public class ValidationException extends IllegalArgumentException {
    private final ValidationResult result;
    private final Object value;

    public ValidationException(Property<?> property, Object value, ValidationResult result) {
        super("Cannot set property " + property.getId() + " to " + value + ": " + result);
        this.result = result;
        this.value = value;
    }

    public ValidationResult getResult() {
        return result;
    }

    public Object getValue() {
        return value;
    }
}
