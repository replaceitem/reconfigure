package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.EnumPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Intermediary;
import net.replaceitem.reconfigure.config.serialization.TypeAdapter;
import net.replaceitem.reconfigure.config.widget.builder.CyclingButtonWidgetBuilderImpl;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

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
        return new CyclingButtonWidgetBuilderImpl<>(propertyBuildContext, this, values)
                .valueToText((T value) -> Text.translatable(id.toTranslationKey(NAMESPACE + ".property", "enum." + value.toString().toLowerCase())));
    }

    @Override
    protected TypeAdapter<T, Intermediary.IntermediaryString> getTypeAdapter() {
        return TypeAdapter.forEnum(values);
    }

    @Override
    protected void preBuild() {
        super.preBuild();
        if(!values.contains(defaultValue)) throw new RuntimeException("Default value isn't one of the values " + values.stream().map(Objects::toString).collect(Collectors.joining(", ")) + " of enum property " + id);
    }
}
