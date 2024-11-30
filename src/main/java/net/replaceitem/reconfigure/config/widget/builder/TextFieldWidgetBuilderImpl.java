package net.replaceitem.reconfigure.config.widget.builder;

import net.replaceitem.reconfigure.api.widget.TextFieldWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.TextFieldConfigWidget;

public class TextFieldWidgetBuilderImpl extends AbstractTextFieldWidgetBuilderImpl<TextFieldWidgetBuilderImpl> implements TextFieldWidgetBuilder {
    public TextFieldWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, String> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }

    @Override
    protected ConfigWidgetFactory<String> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) -> new TextFieldConfigWidget(parent, property, baseSettings, placeholder);
    }
}
