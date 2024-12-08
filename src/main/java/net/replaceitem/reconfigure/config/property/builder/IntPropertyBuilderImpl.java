package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.IntPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Intermediary;
import net.replaceitem.reconfigure.config.serialization.TypeAdapter;
import net.replaceitem.reconfigure.config.widget.builder.SliderWidgetBuilderImpl;

public class IntPropertyBuilderImpl extends NumericPropertyBuilderImpl<IntPropertyBuilder, Integer> implements IntPropertyBuilder {
    public IntPropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id) {
        super(propertyBuildContext, id, 0);
    }

    @Override
    protected TypeAdapter<Integer, Intermediary.IntermediaryInteger> getTypeAdapter() {
        return TypeAdapter.INTEGER;
    }

    @Override
    public SliderWidgetBuilderImpl.IntSliderWidgetBuilder asSlider() {
        assert min != null;
        assert max != null;
        return SliderWidgetBuilderImpl.createInt(propertyBuildContext, this).min(this.min).max(this.max);
    }
}
