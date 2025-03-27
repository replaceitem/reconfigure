package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.CheckboxWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.CyclingButtonWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;

public interface BooleanPropertyBuilder extends PropertyBuilder<BooleanPropertyBuilder, Boolean> {
    CheckboxWidgetBuilder asCheckbox();

    CyclingButtonWidgetBuilder<Boolean> asToggleButton();
}
