package net.replaceitem.reconfigure.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenAxis;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;

public record ColoredAxisQuadGuiElementRenderState(
        RenderPipeline pipeline,
        TextureSetup textureSetup,
        Matrix3x2f pose,
        float x0,
        float y0,
        float x1,
        float y1,
        int col1,
        int col2,
        ScreenAxis axis,
        @Nullable ScreenRectangle scissorArea,
        @Nullable ScreenRectangle bounds
) implements GuiElementRenderState {
    public ColoredAxisQuadGuiElementRenderState(
            RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose, float x0, float y0, float x1, float y1, int col1, int col2, ScreenAxis axis, @Nullable ScreenRectangle scissorArea
    ) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, col1, col2, axis, scissorArea, createBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    @Override
    public void buildVertices(VertexConsumer vertices) {
        vertices.addVertexWith2DPose(this.pose(), this.x0(), this.y0()).setColor(this.col1());
        vertices.addVertexWith2DPose(this.pose(), this.x0(), this.y1()).setColor(this.axis() == ScreenAxis.VERTICAL ? this.col2() : this.col1());
        vertices.addVertexWith2DPose(this.pose(), this.x1(), this.y1()).setColor(this.col2());
        vertices.addVertexWith2DPose(this.pose(), this.x1(), this.y0()).setColor(this.axis() == ScreenAxis.VERTICAL ? this.col1() : this.col2());
    }

    @Nullable
    private static ScreenRectangle createBounds(float x0, float y0, float x1, float y1, Matrix3x2f pose, @Nullable ScreenRectangle scissorArea) {
        ScreenRectangle screenRect = new ScreenRectangle((int) x0, (int) y0, Mth.ceil(x1) - ((int) x0), Mth.ceil(y1) - ((int) y0)).transformMaxBounds(pose);
        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }

    public static ColoredAxisQuadGuiElementRenderState draw(GuiGraphics context, float x0, float y0, float x1, float y1, int col1, int col2, ScreenAxis axis) {
        return new ColoredAxisQuadGuiElementRenderState(
                RenderPipelines.GUI, TextureSetup.noTexture(), new Matrix3x2f(context.pose()), x0, y0, x1, y1, col1, col2, axis, context.scissorStack.peek()
        );
    }
}