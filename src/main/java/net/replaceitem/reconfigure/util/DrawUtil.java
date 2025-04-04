package net.replaceitem.reconfigure.util;

import net.minecraft.client.gui.DrawContext;

public class DrawUtil {
    public static void renderRectOutline(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        context.drawHorizontalLine(x1, x2, y1, color);
        context.drawHorizontalLine(x1, x2, y2, color);
        context.drawVerticalLine(x1, y1, y2, color);
        context.drawVerticalLine(x2, y1, y2, color);
    }

    public static void drawCheckerboard(DrawContext context, int x1, int y1, int x2, int y2, int size, int color) {
        int w = x2-x1;
        int h = y2-y1;
        int stepsY = Math.ceilDiv(h, size);
        for (int y = 0; y < stepsY; y++) {
            int py = y1 + y * size;
            int offset = y % 2 == 0 ? 0 : size;
            int stepsX = Math.ceilDiv(w - offset, size*2);
            for (int x = 0; x < stepsX; x++) {
                int px = x1 + offset + x * size * 2;
                context.fill(px, py, Math.min(x2, px + size), Math.min(y2, py + size), color);
            }
        }
    }
}
