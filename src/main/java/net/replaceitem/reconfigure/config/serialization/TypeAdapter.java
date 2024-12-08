package net.replaceitem.reconfigure.config.serialization;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converts a typed property value to and from an {@link Intermediary} type for serialization.
 * {@link net.replaceitem.reconfigure.api.property.PropertyBuilder}s will attach the correct
 * adapter to the Property when building.
 * The {@link Intermediary} will then be used by the {@link Marshaller} to convert to a Serializer specific type.
 * @param <T> Type of the property
 * @param <M> Type of the intermediary type
 */
public abstract class TypeAdapter<T,M extends Intermediary<?>> {
    private final IntermediaryType<M> type;

    protected TypeAdapter(IntermediaryType<M> type) {
        this.type = type;
    }

    public IntermediaryType<M> getType() {
        return type;
    }

    public abstract T fromIntermediary(M intermediaryType) throws SerializationException;
    public abstract M toIntermediary(T t);
    
    public static final TypeAdapter<String, Intermediary.IntermediaryString> STRING = simple(IntermediaryType.STRING, Intermediary.IntermediaryString::new);
    public static final TypeAdapter<Integer, Intermediary.IntermediaryInteger> INTEGER = simple(IntermediaryType.INTEGER, Intermediary.IntermediaryInteger::new);
    public static final TypeAdapter<Double, Intermediary.IntermediaryDouble> DOUBLE = simple(IntermediaryType.DOUBLE, Intermediary.IntermediaryDouble::new);
    public static final TypeAdapter<Boolean, Intermediary.IntermediaryBoolean> BOOLEAN = simple(IntermediaryType.BOOLEAN, Intermediary.IntermediaryBoolean::new);
    
    public static <T> TypeAdapter<T, Intermediary.IntermediaryString> forEnum(Collection<T> values) {
        return new TypeAdapter<>(IntermediaryType.STRING) {
            @Override
            public T fromIntermediary(Intermediary.IntermediaryString intermediary) throws SerializationException {
                String name = intermediary.getValue();
                return values.stream()
                        .filter(t -> Objects.equals(t.toString(), name))
                        .findFirst()
                        .orElseThrow(() ->
                                new SerializationException("Expected one of " + values.stream()
                                        .map(Objects::toString)
                                        .collect(Collectors.joining(", "))
                                )
                        );
            }

            @Override
            public Intermediary.IntermediaryString toIntermediary(T t) {
                return new Intermediary.IntermediaryString(t.toString());
            }
        };
    }
    
    private static <T,M extends Intermediary<T>> TypeAdapter<T,M> simple(IntermediaryType<M> type, Function<T,M> constructor) {
        return new TypeAdapter<>(type) {
            @Override
            public T fromIntermediary(M intermediaryType) {
                return intermediaryType.getValue();
            }

            @Override
            public M toIntermediary(T t) {
                return constructor.apply(t);
            }
        };
    }
}
