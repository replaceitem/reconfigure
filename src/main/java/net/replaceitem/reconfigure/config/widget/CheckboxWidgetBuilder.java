package net.replaceitem.reconfigure.config.widget;

import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.config.WidgetBuilder;
import net.replaceitem.reconfigure.screen.widget.config.CheckboxConfigWidget;

public class CheckboxWidgetBuilder extends WidgetBuilder<CheckboxWidgetBuilder, Boolean> {
    public CheckboxWidgetBuilder(PropertyBuilder<?, Boolean> propertyBuilder) {
        super(propertyBuilder);
    }

    @Override
    protected ConfigWidgetFactory<Boolean> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) -> new CheckboxConfigWidget(parent, property, baseSettings);
    }
}
