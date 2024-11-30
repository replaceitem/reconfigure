package net.replaceitem.reconfigure.config.widget.builder;

import net.replaceitem.reconfigure.api.widget.CheckboxWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.CheckboxConfigWidget;

public class CheckboxWidgetBuilderImpl extends WidgetBuilderImpl<CheckboxWidgetBuilder, Boolean> implements CheckboxWidgetBuilder {
    public CheckboxWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, Boolean> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }

    public ConfigWidgetFactory<Boolean> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) -> new CheckboxConfigWidget(parent, property, baseSettings);
    }
}
