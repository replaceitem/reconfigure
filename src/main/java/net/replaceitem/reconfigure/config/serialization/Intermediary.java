package net.replaceitem.reconfigure.config.serialization;

public abstract sealed class Intermediary<T> permits
        Intermediary.IntermediaryString,
        Intermediary.IntermediaryInteger,
        Intermediary.IntermediaryDouble,
        Intermediary.IntermediaryBoolean
{
    private final T value;

    public T getValue() {
        return value;
    }

    protected Intermediary(T value) {
        this.value = value;
    }

    public static final class IntermediaryString extends Intermediary<String> {
        public IntermediaryString(String value) {
            super(value);
        }
    }

    public static final class IntermediaryInteger extends Intermediary<Integer> {
        public IntermediaryInteger(Integer value) {
            super(value);
        }
    }

    public static final class IntermediaryDouble extends Intermediary<Double> {
        public IntermediaryDouble(Double value) {
            super(value);
        }
    }

    public static final class IntermediaryBoolean extends Intermediary<Boolean> {
        public IntermediaryBoolean(Boolean value) {
            super(value);
        }
    }
}
