package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * Helper record for passing all settings that are present on all widgets in one parameter
 */
public record BaseSettings(
        Text displayName,
        @Nullable Text tooltip
) {

}
