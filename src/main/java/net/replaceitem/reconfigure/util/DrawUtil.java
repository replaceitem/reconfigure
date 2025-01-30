package net.replaceitem.reconfigure.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.replaceitem.reconfigure.Shaders;

import java.util.function.Consumer;

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

    @SuppressWarnings("resource")
    public static void drawWithColorpickerShader(DrawContext context, Consumer<VertexConsumer> vertexConsumer) {
        context.draw(); // since we draw the shader immediately but DrawContext calls like .fill are buffered and drawn later, we need to flush all pending draws for proper z order
        ShaderProgram previousShader = RenderSystem.getShader();
        RenderSystem.setShader(Shaders.COLORPICKER);
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        vertexConsumer.accept(buffer);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        RenderSystem.setShader(previousShader);
    }
}
