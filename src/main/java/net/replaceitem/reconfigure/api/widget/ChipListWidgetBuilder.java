package net.replaceitem.reconfigure.api.widget;

import java.util.List;

public interface ChipListWidgetBuilder extends WidgetBuilder<ChipListWidgetBuilder, List<String>> {
    /**
     * Sets whether the chips of the chip list are editable. Each chip is its own text field and this
     * allows toggling whether they can be edited or only added and removed.
     * Defaults to true.
     * @param editable Whether the chips are editable
     * @return The builder for chaining
     */
    ChipListWidgetBuilder chipsEditable(boolean editable);
}
