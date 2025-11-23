package net.replaceitem.reconfigure.mixin;

import net.minecraft.client.gui.components.StringWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StringWidget.class)
public interface StringWidgetAccessor {
    @Accessor
    int getMaxWidth();
}
