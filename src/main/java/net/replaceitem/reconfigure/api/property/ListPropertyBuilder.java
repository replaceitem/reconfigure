package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.config.widget.builder.ChipListWidgetBuilderImpl;

import java.util.List;

public interface ListPropertyBuilder extends PropertyBuilder<ListPropertyBuilder, List<String>> {
    ChipListWidgetBuilderImpl asChipList();
}
