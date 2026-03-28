package net.replaceitem.reconfigure.extensions;

import org.spongepowered.asm.mixin.Unique;

public interface CheckboxAccess {
    @Unique
    void reconfigure$setSelected(boolean selected);
}
