package net.replaceitem.reconfigure.config.serialization;

import java.util.List;

public abstract sealed class Intermediary<T> permits Intermediary.IntermediaryBoolean, Intermediary.IntermediaryDouble, Intermediary.IntermediaryInteger, Intermediary.IntermediaryList, Intermediary.IntermediaryString
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
    
    public static final class IntermediaryList extends Intermediary<List<String>> {
        public IntermediaryList(List<String> value) {
            super(value);
        }
    }
}
