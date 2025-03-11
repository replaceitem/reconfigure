package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.ChipListWidgetBuilder;

import java.util.List;

public interface ListPropertyBuilder extends PropertyBuilder<ListPropertyBuilder, List<String>> {
    ChipListWidgetBuilder asChipList();
}
