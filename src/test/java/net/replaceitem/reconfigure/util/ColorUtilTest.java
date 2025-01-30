package net.replaceitem.reconfigure.util;

import net.minecraft.util.math.ColorHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorUtilTest {
    @Test
    void testColorConversion() {
        int[] channelValues = new int[]{0x00, 0x01, 0x50, 0x7F, 0x80, 0x81, 0xA0, 0xFE, 0xFF};
        for (int r : channelValues) {
            for (int g : channelValues) {
                for (int b : channelValues) {
                    int color = ColorHelper.getArgb(r, g, b);
                    ColorUtil.HSVColor floats = ColorUtil.rgbToHsvFloats(color);
                    int converted = ColorUtil.hsvToRgb(floats.hue(), floats.saturation(), floats.value());
                    assertEquals(color, converted,
                            String.format("Expected: 0x%06X, but got: 0x%06X", color, converted));
                }
            }
        }
    }
}
