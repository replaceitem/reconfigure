package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.DoublePropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.widget.builder.SliderWidgetBuilderImpl;

public class DoublePropertyBuilderImpl extends NumericPropertyBuilderImpl<DoublePropertyBuilder, Double> implements DoublePropertyBuilder {
    public DoublePropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id) {
        super(propertyBuildContext, id);
        defaultValue = 0.0;
    }
    
    @Override
    public SliderWidgetBuilderImpl<?, Double> asSlider() {
        return SliderWidgetBuilderImpl.createDouble(propertyBuildContext, this).min(min).max(max);
    }
}
