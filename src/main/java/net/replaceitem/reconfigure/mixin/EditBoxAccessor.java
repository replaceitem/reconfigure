package net.replaceitem.reconfigure.mixin;

import net.minecraft.client.gui.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EditBox.class)
public interface EditBoxAccessor {
    @Mutable
    @Accessor
    void setWidth(int width);
    
    @Invoker()
    void callRewrap();
}
