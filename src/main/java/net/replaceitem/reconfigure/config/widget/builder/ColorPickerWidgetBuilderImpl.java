package net.replaceitem.reconfigure.config.widget.builder;

import net.replaceitem.reconfigure.api.widget.ColorPickerWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.ColorPickerConfigWidget;

public class ColorPickerWidgetBuilderImpl extends WidgetBuilderImpl<ColorPickerWidgetBuilder, Integer> implements ColorPickerWidgetBuilder {
    public ColorPickerWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, Integer> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }

    @Override
    protected ConfigWidgetFactory<Integer> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) -> new ColorPickerConfigWidget(parent, property, baseSettings);
    }
}
