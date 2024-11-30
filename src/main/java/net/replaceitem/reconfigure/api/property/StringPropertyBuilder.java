package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.config.widget.builder.EditBoxWidgetBuilderImpl;
import net.replaceitem.reconfigure.config.widget.builder.TextFieldWidgetBuilderImpl;

public interface StringPropertyBuilder extends PropertyBuilder<StringPropertyBuilder, String> {
    TextFieldWidgetBuilderImpl asTextField();

    EditBoxWidgetBuilderImpl asEditBox();
}
