package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.IntPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Caster;
import net.replaceitem.reconfigure.config.widget.builder.SliderWidgetBuilderImpl;

public class IntPropertyBuilderImpl extends NumericPropertyBuilderImpl<IntPropertyBuilder, Integer> implements IntPropertyBuilder {
    public IntPropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id) {
        super(propertyBuildContext, id);
        defaultValue = 0;
    }

    @Override
    protected Caster<Integer> getCaster() {
        return Caster.INT;
    }

    @Override
    public SliderWidgetBuilderImpl.IntSliderWidgetBuilder asSlider() {
        return SliderWidgetBuilderImpl.createInt(propertyBuildContext, this).min(this.min).max(this.max);
    }
}
