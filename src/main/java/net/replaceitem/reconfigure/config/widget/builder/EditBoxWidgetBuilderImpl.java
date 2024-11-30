package net.replaceitem.reconfigure.config.widget.builder;

import net.replaceitem.reconfigure.api.widget.EditBoxWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.EditBoxConfigWidget;

public class EditBoxWidgetBuilderImpl extends AbstractTextFieldWidgetBuilderImpl<EditBoxWidgetBuilderImpl> implements EditBoxWidgetBuilder {
    public EditBoxWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, String> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }

    @Override
    protected ConfigWidgetFactory<String> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) -> new EditBoxConfigWidget(parent, property, baseSettings, placeholder);
    }
}
