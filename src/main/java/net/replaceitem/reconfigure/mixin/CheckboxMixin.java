package net.replaceitem.reconfigure.mixin;

import net.minecraft.client.gui.components.Checkbox;
import net.replaceitem.reconfigure.extensions.CheckboxAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Checkbox.class)
public class CheckboxMixin implements CheckboxAccess {
    @Shadow private boolean selected;
    @Shadow @Final private Checkbox.OnValueChange onValueChange;

    @Unique
    @Override
    public void reconfigure$setSelected(boolean selected) {
        this.selected = selected;
        this.onValueChange.onValueChange(((Checkbox) (Object) this), selected);
    }
}
