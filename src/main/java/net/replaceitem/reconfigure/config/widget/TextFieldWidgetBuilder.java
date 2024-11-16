package net.replaceitem.reconfigure.config.widget;

import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.config.WidgetBuilder;
import net.replaceitem.reconfigure.screen.widget.TextFieldConfigWidget;

public class TextFieldWidgetBuilder extends WidgetBuilder<TextFieldWidgetBuilder, String> {
    public TextFieldWidgetBuilder(PropertyBuilder<?, String> propertyBuilder) {
        super(propertyBuilder);
    }

    @Override
    protected ConfigWidgetFactory<String> buildWidgetFactory() {
        return (parent, property) -> new TextFieldConfigWidget(parent, property, displayName);
    }
}
