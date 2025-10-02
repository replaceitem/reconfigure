package net.replaceitem.reconfigure.mixin;

import net.minecraft.client.gui.widget.TextWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextWidget.class)
public interface TextWidgetAccessor {
    @Accessor
    int getMaxWidth();
}
