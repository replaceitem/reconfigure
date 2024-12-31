package net.replaceitem.reconfigure.config.serialization;

public class IntermediaryType<M extends Intermediary<?>> {
    public static final IntermediaryType<Intermediary.IntermediaryString> STRING = new IntermediaryType<>("string");
    public static final IntermediaryType<Intermediary.IntermediaryInteger> INTEGER = new IntermediaryType<>("integer");
    public static final IntermediaryType<Intermediary.IntermediaryDouble> DOUBLE = new IntermediaryType<>("double");
    public static final IntermediaryType<Intermediary.IntermediaryBoolean> BOOLEAN = new IntermediaryType<>("boolean");
    public static final IntermediaryType<Intermediary.IntermediaryList> LIST = new IntermediaryType<>("list");
    
    private final String name;

    public IntermediaryType(String name) {
        this.name = name;
    }
    
    @SuppressWarnings("unchecked")
    public M cast(Object o) {
        return (M) o;
    }

    @Override
    public String toString() {
        return name;
    }
}
