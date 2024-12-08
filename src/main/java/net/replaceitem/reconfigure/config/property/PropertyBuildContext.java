package net.replaceitem.reconfigure.config.property;

import net.replaceitem.reconfigure.config.PropertyHolder;

public interface PropertyBuildContext {
    void addProperty(PropertyHolder<?> property);
}
