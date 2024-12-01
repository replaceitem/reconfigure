package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.CyclingButtonWidgetBuilder;
import net.replaceitem.reconfigure.config.widget.builder.CheckboxWidgetBuilderImpl;

public interface BooleanPropertyBuilder extends PropertyBuilder<BooleanPropertyBuilder, Boolean> {
    CheckboxWidgetBuilderImpl asCheckbox();

    CyclingButtonWidgetBuilder<Boolean> asToggleButton();
}
