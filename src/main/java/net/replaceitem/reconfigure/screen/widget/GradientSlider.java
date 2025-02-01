package net.replaceitem.reconfigure.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.replaceitem.reconfigure.util.DrawUtil;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public class GradientSlider extends SliderWidget {
    private final Int2IntFunction colorSupplier;

    private boolean isHsv;
    
    public GradientSlider(int x, int y, int width, int height, Text text, double value, Int2IntFunction colorSupplier) {
        super(x, y, width, height, text, value);
        this.colorSupplier = colorSupplier;
        this.updateMessage();
    }
    
    public void setHsv(boolean hsv) {
        isHsv = hsv;
    }

    public void setValue(double value) {
        double d = this.value;
        this.value = MathHelper.clamp(value, 0.0, 1.0);
        if (d != this.value) {
            this.applyValue();
        }

        this.updateMessage();
    }
    
    public double getValue() {
        return this.value;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.fill(getX(), getY(), getRight(), getBottom(), Colors.BLACK);
        if(ColorHelper.getAlpha(colorSupplier.get(0)) < 255 || ColorHelper.getAlpha(colorSupplier.get(1)) < 255) {
            context.fill(getX()+1, getY()+1, getRight()-1, getBottom()-1, Colors.WHITE);
            DrawUtil.drawCheckerboard(context, getX()+1, getY()+1, getRight()-1, getBottom()-1, 3, Colors.LIGHT_GRAY);
            RenderSystem.enableBlend();
        }
        
        Consumer<VertexConsumer> drawer = getGradientDrawer(context);
        if(isHsv) {
            DrawUtil.drawWithColorpickerShader(context, drawer);
        } else {
            context.draw(vertexConsumerProvider -> drawer.accept(vertexConsumerProvider.getBuffer(RenderLayer.getGui())));
        }
        RenderSystem.disableBlend();

        int handleX = this.getX() + (int) (this.value * (double) (this.width - 8));
        DrawUtil.renderRectOutline(context, handleX, getY(), handleX+8-1, getBottom()-1, ColorHelper.withAlpha(ColorHelper.channelFromFloat(this.alpha), this.hovered ? Colors.WHITE : Colors.LIGHT_GRAY));
        
        int i = this.active ? 16777215 : 10526880;
        this.drawScrollableText(context, minecraftClient.textRenderer, 2, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    private Consumer<VertexConsumer> getGradientDrawer(DrawContext context) {
        ScreenRect colorRect = getColorRect();

        return (vertexConsumer) -> {
            Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
            int colorA = colorSupplier.get(0);
            int colorB = colorSupplier.get(1);
            vertexConsumer.vertex(matrix, colorRect.getLeft(), colorRect.getTop(), 0).color(colorA);
            vertexConsumer.vertex(matrix, colorRect.getLeft(), colorRect.getBottom(), 0).color(colorA);
            vertexConsumer.vertex(matrix, colorRect.getRight(), colorRect.getBottom(), 0).color(colorB);
            vertexConsumer.vertex(matrix, colorRect.getRight(), colorRect.getTop(), 0).color(colorB);
        };
    }

    private ScreenRect getColorRect() {
        return new ScreenRect(getX()+1, getY()+1, getWidth()-2, getHeight()-2);
    }
    
    @Override
    protected void updateMessage() {
        this.setMessage(Text.literal(String.format("%.1f%%", value*100)));
    }

    @Override
    protected void applyValue() {

    }
}
