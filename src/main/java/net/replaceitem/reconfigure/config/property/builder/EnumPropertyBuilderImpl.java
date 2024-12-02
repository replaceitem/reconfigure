package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.EnumPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Caster;
import net.replaceitem.reconfigure.config.widget.builder.CyclingButtonWidgetBuilderImpl;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class EnumPropertyBuilderImpl<T> extends PropertyBuilderImpl<EnumPropertyBuilder<T>, T> implements EnumPropertyBuilder<T> {
    private final Collection<T> values;

    public EnumPropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id, Collection<T> values) {
        super(propertyBuildContext, id, values.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("EnumProperty " + id + " cannot have no values, must be at least one."))
        );
        this.values = values;
    }

    @Override
    public CyclingButtonWidgetBuilderImpl<T> asCyclingButton() {
        return new CyclingButtonWidgetBuilderImpl<>(propertyBuildContext, this, values);
    }

    @Override
    protected Caster<T> getCaster() {
        return Caster.forEnum(values);
    }

    @Override
    protected void preBuild() {
        super.preBuild();
        if(!values.contains(defaultValue)) throw new RuntimeException("Default value isn't one of the values " + values.stream().map(Objects::toString).collect(Collectors.joining(", ")) + " of enum property " + id);
    }
}
