package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.CheckboxWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.CyclingButtonWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;

public interface BooleanPropertyBuilder extends PropertyBuilder<BooleanPropertyBuilder, Boolean> {
    /**
     * Finishes building the boolean property and starts building a checkbox widget for it.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The checkbox widget builder
     */
    CheckboxWidgetBuilder asCheckbox();

    /**
     * Finishes building the boolean property and starts building a cycling button widget for it.
     * A cycling button widget is a button that cycles between two values for true and false when clicked.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The cycling button widget builder
     */
    CyclingButtonWidgetBuilder<Boolean> asToggleButton();
}
