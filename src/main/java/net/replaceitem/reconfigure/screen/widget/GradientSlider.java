package net.replaceitem.reconfigure.screen.widget;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.navigation.NavigationAxis;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.replaceitem.reconfigure.util.ColoredAxisQuadGuiElementRenderState;
import net.replaceitem.reconfigure.util.DrawUtil;
import net.replaceitem.reconfigure.util.HueGradientQuadGuiElementRenderState;

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
        }

        ScreenRect colorRect = this.getColorRect();
        if(isHsv) {
            context.state.addSimpleElement(HueGradientQuadGuiElementRenderState.draw(
                    context, colorRect.getLeft(), colorRect.getTop(), colorRect.getRight(), colorRect.getBottom()
            ));
        } else {
            int colorA = colorSupplier.get(0);
            int colorB = colorSupplier.get(1);
            context.state.addSimpleElement(ColoredAxisQuadGuiElementRenderState.draw(
                    context, colorRect.getLeft(), colorRect.getTop(), colorRect.getRight(), colorRect.getBottom(), colorA, colorB, NavigationAxis.HORIZONTAL
            ));
        }

        int handleX = this.getX() + (int) (this.value * (double) (this.width - 8));
        DrawUtil.renderRectOutline(context, handleX, getY(), handleX+8-1, getBottom()-1, ColorHelper.withAlpha(ColorHelper.channelFromFloat(this.alpha), this.hovered ? Colors.WHITE : Colors.LIGHT_GRAY));
        
        int i = this.active ? 16777215 : 10526880;
        this.drawScrollableText(context, minecraftClient.textRenderer, 2, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
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
