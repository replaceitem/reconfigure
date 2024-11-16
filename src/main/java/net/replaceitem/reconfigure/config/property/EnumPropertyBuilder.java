package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.config.widget.CyclingButtonWidgetBuilder;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EnumPropertyBuilder<T> extends PropertyBuilder<EnumPropertyBuilder<T>, T> {
    private final Collection<T> values;

    public EnumPropertyBuilder(Consumer<Property<T>> propertyConsumer, Identifier id, Collection<T> values) {
        super(propertyConsumer, id);
        if(values.isEmpty()) throw new IllegalArgumentException("EnumProperty cannot have no values, must be at least one.");
        this.values = values;
        this.defaultValue = values.stream().findFirst().orElseThrow();
    }

    public CyclingButtonWidgetBuilder<T> asCyclingButton() {
        return new CyclingButtonWidgetBuilder<>(this, values);
    }
    
    @Override
    protected void preBuild() {
        super.preBuild();
        if(!values.contains(defaultValue)) throw new RuntimeException("Default value isn't one of the values " + values.stream().map(Objects::toString).collect(Collectors.joining(", ")) + " of enum property " + id);
    }
}
