package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.CyclingButtonWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;

public interface EnumPropertyBuilder<T> extends PropertyBuilder<EnumPropertyBuilder<T>, T> {
    /**
     * Finishes building the enum property and starts building a cycling button widget for it.
     * A cycling button widget is a button that cycles through all possible enum values when clicked.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The cycling button widget builder
     */
    CyclingButtonWidgetBuilder<T> asCyclingButton();
}
