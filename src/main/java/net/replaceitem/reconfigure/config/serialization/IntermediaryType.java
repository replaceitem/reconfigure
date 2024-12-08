package net.replaceitem.reconfigure.config.serialization;

public class IntermediaryType<M extends Intermediary<?>> {
    @SuppressWarnings("unchecked")
    public M cast(Object o) {
        return (M) o;
    }
    
    public static final IntermediaryType<Intermediary.IntermediaryString> STRING = new IntermediaryType<>();
    public static final IntermediaryType<Intermediary.IntermediaryInteger> INTEGER = new IntermediaryType<>();
    public static final IntermediaryType<Intermediary.IntermediaryDouble> DOUBLE = new IntermediaryType<>();
    public static final IntermediaryType<Intermediary.IntermediaryBoolean> BOOLEAN = new IntermediaryType<>();
}
