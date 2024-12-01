package net.replaceitem.reconfigure.config.serialization;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Caster<T> {
    T cast(Object o) throws CastingException;
    
    Caster<String> STRING = o -> {
        if(!(o instanceof String str)) throw new CastingException("Expected a string");
        return str;
    };
    Caster<Integer> INT = o -> {
        if(!(o instanceof Number num)) throw new CastingException("Expected a string");
        return num.intValue();
    };
    Caster<Double> DOUBLE = o -> {
        if(!(o instanceof Number num)) throw new CastingException("Expected a string");
        return num.doubleValue();
    };
    Caster<Boolean> BOOLEAN = o -> {
        if(!(o instanceof Boolean bool)) throw new CastingException("Expected a string");
        return bool;
    };
    
    static <T> Caster<T> forEnum(Collection<T> values) {
        return o -> {
            if(!(o instanceof String value)) throw new CastingException("Expected a string");
            return values.stream()
                    .filter(t -> Objects.equals(t.toString(), value))
                    .findFirst()
                    .orElseThrow(() ->
                            new CastingException("Expected one of " + values.stream()
                                    .map(Objects::toString)
                                    .collect(Collectors.joining(", "))
                            )
                    );
        };
    }
}
