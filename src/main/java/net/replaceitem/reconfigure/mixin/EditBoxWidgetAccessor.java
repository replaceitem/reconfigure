package net.replaceitem.reconfigure.mixin;

import net.minecraft.client.gui.EditBox;
import net.minecraft.client.gui.widget.EditBoxWidget;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Debug(export = true)
@Mixin(EditBoxWidget.class)
public interface EditBoxWidgetAccessor {
    @Accessor
    EditBox getEditBox();
}
