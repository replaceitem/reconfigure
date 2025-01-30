package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.IntPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Intermediary;
import net.replaceitem.reconfigure.config.serialization.TypeAdapter;
import net.replaceitem.reconfigure.api.widget.ColorPickerWidgetBuilder;
import net.replaceitem.reconfigure.config.widget.builder.ColorPickerWidgetBuilderImpl;
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
        if(this.min == null) throw new RuntimeException("min is required for a slider widget");
        if(this.max == null) throw new RuntimeException("max is required for a slider widget");
        return SliderWidgetBuilderImpl.createInt(propertyBuildContext, this).min(this.min).max(this.max);
    }

    @Override
    public ColorPickerWidgetBuilder asColorPicker() {
        return new ColorPickerWidgetBuilderImpl(propertyBuildContext, this);
    }
}
