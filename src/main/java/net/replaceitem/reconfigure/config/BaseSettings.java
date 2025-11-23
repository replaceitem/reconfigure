package net.replaceitem.reconfigure.config;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

/**
 * Helper record for passing all settings that are present on all widgets in one parameter
 */
public record BaseSettings(
        Component displayName,
        @Nullable Component tooltip
) {

}
