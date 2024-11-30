package net.replaceitem.reconfigure.config.property;

import net.replaceitem.reconfigure.config.widget.Widget;

public interface PropertyBuildContext {
    void addProperty(PropertyImpl<?> property);
    void addWidget(Widget<?> widget);
}
