package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.ChipListWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;

import java.util.List;

public interface ListPropertyBuilder extends PropertyBuilder<ListPropertyBuilder, List<String>> {
    /**
     * Finishes building the list property and starts building a chip list widget for it.
     * A chip list widget has a text field into which values can be entered. When clicking a button,
     * the value is added to the list and shown in a chip. Each chip can be removed.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The chip list widget builder
     */
    ChipListWidgetBuilder asChipList();
}
