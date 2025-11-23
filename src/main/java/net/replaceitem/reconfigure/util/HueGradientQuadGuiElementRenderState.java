package net.replaceitem.reconfigure.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.renderer.RenderPipelines;
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
        @Nullable ScreenRectangle scissorArea,
        @Nullable ScreenRectangle bounds
) implements GuiElementRenderState {
    public HueGradientQuadGuiElementRenderState(
            RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose, int x0, int y0, int x1, int y1, @Nullable ScreenRectangle scissorArea
    ) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, scissorArea, createBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    private static final int[] HSV_GRADIENT_COLORS = {0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFFFF00FF};

    @Override
    public void buildVertices(VertexConsumer vertices) {
        for (int i = 0; i < HSV_GRADIENT_COLORS.length; i++) {
            int colorStart = HSV_GRADIENT_COLORS[i];
            int colorEnd = HSV_GRADIENT_COLORS[(i+1) % HSV_GRADIENT_COLORS.length];
            float xStart = x0 + (x1-x0) / HSV_GRADIENT_COLORS.length * i;
            float xEnd = x0 + (x1-x0) / HSV_GRADIENT_COLORS.length * (i + 1);
            vertices
                    .addVertexWith2DPose(this.pose(), xStart, y0).setColor(colorStart)
                    .addVertexWith2DPose(this.pose(), xStart, y1).setColor(colorStart)
                    .addVertexWith2DPose(this.pose(), xEnd, y1).setColor(colorEnd)
                    .addVertexWith2DPose(this.pose(), xEnd, y0).setColor(colorEnd);
        }
    }

    @Nullable
    private static ScreenRectangle createBounds(int x0, int y0, int x1, int y1, Matrix3x2f pose, @Nullable ScreenRectangle scissorArea) {
        ScreenRectangle screenRect = new ScreenRectangle(x0, y0, x1 - x0, y1 - y0).transformMaxBounds(pose);
        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }

    public static HueGradientQuadGuiElementRenderState draw(GuiGraphics context, int x0, int y0, int x1, int y1) {
        return new HueGradientQuadGuiElementRenderState(
                RenderPipelines.GUI, TextureSetup.noTexture(), new Matrix3x2f(context.pose()), x0, y0, x1, y1, context.scissorStack.peek()
        );
    }
}