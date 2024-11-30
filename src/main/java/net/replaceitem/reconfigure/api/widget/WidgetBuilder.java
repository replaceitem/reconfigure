package net.replaceitem.reconfigure.api.widget;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.Property;

public interface WidgetBuilder<SELF extends WidgetBuilder<SELF, T>, T> {
    SELF displayName(Text displayName);

    SELF tooltip(Tooltip tooltip);

    SELF tooltip(Text tooltipText);

    Property<T> build();
}
