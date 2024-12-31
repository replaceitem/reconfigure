package net.replaceitem.reconfigure.api.widget;

import java.util.List;

public interface ChipListWidgetBuilder extends WidgetBuilder<ChipListWidgetBuilder, List<String>> {
    ChipListWidgetBuilder chipsEditable(boolean editable);
}
