package net.replaceitem.reconfigure.util;

import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("DuplicatedCode")
public class ColorUtil {
    public static int hsvToRgb(float hue, float saturation, float value) {
        float r = getRgbComponent(1.0f, hue, saturation, value);
        float g = getRgbComponent(2.0f/3.0f, hue, saturation, value);
        float b = getRgbComponent(1.0f/3.0f, hue, saturation, value);
        return ColorHelper.getArgb(roundFloatToInt(r), roundFloatToInt(g), roundFloatToInt(b));
    }
    
    private static int roundFloatToInt(float f) {
        return Math.round(f*255);
    }

    /*
        Replica of the colorpicker shader:
        vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
        vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
        return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
     */
    private static float getRgbComponent(float k, float hue, float saturation, float value) {
        float p = Math.abs(MathHelper.fractionalPart(hue + k) * 6.0f - 3.0f);
        return value * MathHelper.lerp(saturation, 1.0f, MathHelper.clamp(p-1, 0, 1));
    }

    public static HSVColor rgbToHsvFloats(int rgb) {
        int red = ColorHelper.getRed(rgb);
        int green = ColorHelper.getGreen(rgb);
        int blue = ColorHelper.getBlue(rgb);
        
        int value = Math.max(Math.max(red, green), blue);
        int minChannel = Math.min(Math.min(red, green), blue);
        float saturation = 1.0f - minChannel / (value == 0 ? 1.0f : value);

        float redNormalized = MathHelper.getLerpProgress(red, minChannel, value);
        float greenNormalized = MathHelper.getLerpProgress(green, minChannel, value);
        float blueNormalized = MathHelper.getLerpProgress(blue, minChannel, value);

        float hue;

        if(blue == minChannel) hue = greenNormalized + (1-redNormalized);
        else if(red == minChannel) hue = 2 + blueNormalized + (1-greenNormalized);
        else hue = 4 + redNormalized + (1-blueNormalized);

        if(Float.isNaN(hue)) hue = 0;

        hue /= 6;

        return new HSVColor(
                hue,
                saturation,
                ColorHelper.getBlueFloat(value)
        );
    }
    
    public record HSVColor(float hue, float saturation, float value) {}
}
