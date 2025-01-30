package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.DoublePropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Intermediary;
import net.replaceitem.reconfigure.config.serialization.TypeAdapter;
import net.replaceitem.reconfigure.config.widget.builder.SliderWidgetBuilderImpl;

public class DoublePropertyBuilderImpl extends NumericPropertyBuilderImpl<DoublePropertyBuilder, Double> implements DoublePropertyBuilder {
    public DoublePropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id) {
        super(propertyBuildContext, id, 0.0);
    }

    @Override
    protected TypeAdapter<Double, Intermediary.IntermediaryDouble> getTypeAdapter() {
        return TypeAdapter.DOUBLE;
    }

    @Override
    public SliderWidgetBuilderImpl<?, Double> asSlider() {
        if(this.min == null) throw new RuntimeException("min is required for a slider widget");
        if(this.max == null) throw new RuntimeException("max is required for a slider widget");
        return SliderWidgetBuilderImpl.createDouble(propertyBuildContext, this).min(min).max(max);
    }
}
