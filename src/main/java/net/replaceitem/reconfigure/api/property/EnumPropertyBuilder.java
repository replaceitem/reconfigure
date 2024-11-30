package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.CyclingButtonWidgetBuilder;

public interface EnumPropertyBuilder<T> extends PropertyBuilder<EnumPropertyBuilder<T>, T> {
    CyclingButtonWidgetBuilder<T> asCyclingButton();
}
