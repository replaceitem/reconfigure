package net.replaceitem.reconfigure.config.widget;

import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.screen.widget.config.EditBoxConfigWidget;

public class EditBoxWidgetBuilder extends AbstractTextFieldWidgetBuilder<EditBoxWidgetBuilder> {
    public EditBoxWidgetBuilder(PropertyBuilder<?, String> propertyBuilder) {
        super(propertyBuilder);
    }

    @Override
    protected ConfigWidgetFactory<String> buildWidgetFactory() {
        return (parent, property) -> new EditBoxConfigWidget(parent, property, displayName, placeholder);
    }
}
