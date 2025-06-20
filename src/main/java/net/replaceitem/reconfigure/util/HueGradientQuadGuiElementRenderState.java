package net.replaceitem.reconfigure.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;

public record HueGradientQuadGuiElementRenderState(
        RenderPipeline pipeline,
        TextureSetup textureSetup,
        Matrix3x2f pose,
        float x0,
        float y0,
        float x1,
        float y1,
        @Nullable ScreenRect scissorArea,
        @Nullable ScreenRect bounds
) implements SimpleGuiElementRenderState {
    public HueGradientQuadGuiElementRenderState(
            RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose, int x0, int y0, int x1, int y1, @Nullable ScreenRect scissorArea
    ) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, scissorArea, createBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    private static final int[] HSV_GRADIENT_COLORS = {0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFFFF00FF};

    @Override
    public void setupVertices(VertexConsumer vertices, float depth) {
        for (int i = 0; i < HSV_GRADIENT_COLORS.length; i++) {
            int colorStart = HSV_GRADIENT_COLORS[i];
            int colorEnd = HSV_GRADIENT_COLORS[(i+1) % HSV_GRADIENT_COLORS.length];
            float xStart = x0 + (x1-x0) / HSV_GRADIENT_COLORS.length * i;
            float xEnd = x0 + (x1-x0) / HSV_GRADIENT_COLORS.length * (i + 1);
            vertices
                    .vertex(this.pose(), xStart, y0, depth).color(colorStart)
                    .vertex(this.pose(), xStart, y1, depth).color(colorStart)
                    .vertex(this.pose(), xEnd, y1, depth).color(colorEnd)
                    .vertex(this.pose(), xEnd, y0, depth).color(colorEnd);
        }
    }

    @Nullable
    private static ScreenRect createBounds(int x0, int y0, int x1, int y1, Matrix3x2f pose, @Nullable ScreenRect scissorArea) {
        ScreenRect screenRect = new ScreenRect(x0, y0, x1 - x0, y1 - y0).transformEachVertex(pose);
        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }

    public static HueGradientQuadGuiElementRenderState draw(DrawContext context, int x0, int y0, int x1, int y1) {
        return new HueGradientQuadGuiElementRenderState(
                RenderPipelines.GUI, TextureSetup.empty(), new Matrix3x2f(context.getMatrices()), x0, y0, x1, y1, context.scissorStack.peekLast()
        );
    }
}