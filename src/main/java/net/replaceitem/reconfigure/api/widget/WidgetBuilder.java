package net.replaceitem.reconfigure.api.widget;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.Property;

public interface WidgetBuilder<SELF extends WidgetBuilder<SELF, T>, T> {
    SELF displayName(Text displayName);

    SELF tooltip(Text tooltip);
    SELF tooltip();

    Property<T> build();
}
