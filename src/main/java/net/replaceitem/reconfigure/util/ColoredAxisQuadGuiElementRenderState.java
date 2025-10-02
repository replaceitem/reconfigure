package net.replaceitem.reconfigure.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.navigation.NavigationAxis;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import net.minecraft.util.math.MathHelper;
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
        NavigationAxis axis,
        @Nullable ScreenRect scissorArea,
        @Nullable ScreenRect bounds
) implements SimpleGuiElementRenderState {
    public ColoredAxisQuadGuiElementRenderState(
            RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose, float x0, float y0, float x1, float y1, int col1, int col2, NavigationAxis axis, @Nullable ScreenRect scissorArea
    ) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, col1, col2, axis, scissorArea, createBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    @Override
    public void setupVertices(VertexConsumer vertices) {
        vertices.vertex(this.pose(), this.x0(), this.y0()).color(this.col1());
        vertices.vertex(this.pose(), this.x0(), this.y1()).color(this.axis() == NavigationAxis.VERTICAL ? this.col2() : this.col1());
        vertices.vertex(this.pose(), this.x1(), this.y1()).color(this.col2());
        vertices.vertex(this.pose(), this.x1(), this.y0()).color(this.axis() == NavigationAxis.VERTICAL ? this.col1() : this.col2());
    }

    @Nullable
    private static ScreenRect createBounds(float x0, float y0, float x1, float y1, Matrix3x2f pose, @Nullable ScreenRect scissorArea) {
        ScreenRect screenRect = new ScreenRect((int) x0, (int) y0, MathHelper.ceil(x1) - ((int) x0), MathHelper.ceil(y1) - ((int) y0)).transformEachVertex(pose);
        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }

    public static ColoredAxisQuadGuiElementRenderState draw(DrawContext context, float x0, float y0, float x1, float y1, int col1, int col2, NavigationAxis axis) {
        return new ColoredAxisQuadGuiElementRenderState(
                RenderPipelines.GUI, TextureSetup.empty(), new Matrix3x2f(context.getMatrices()), x0, y0, x1, y1, col1, col2, axis, context.scissorStack.peekLast()
        );
    }
}