package net.replaceitem.reconfigure.testmod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.replaceitem.reconfigure.testmod.Testmod.CONFIG;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {
    @Shadow public abstract void setScreen(@Nullable Screen screen);

    @Inject(method = "onResourceLoadFinished", at = @At("RETURN"))
    public void openTestConfigScreen(CallbackInfo ci) {
        this.setScreen(CONFIG.CONFIG.createScreen(null));
    }
}
