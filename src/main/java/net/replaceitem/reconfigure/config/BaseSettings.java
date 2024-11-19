package net.replaceitem.reconfigure.config;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

/**
 * Helper record for passing all settings that are present on all widgets in one parameter
 */
public record BaseSettings(
        Text displayName,
        Tooltip tooltip
) {

}
