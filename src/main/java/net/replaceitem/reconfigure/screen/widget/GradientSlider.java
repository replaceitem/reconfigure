package net.replaceitem.reconfigure.screen.widget;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.navigation.ScreenAxis;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.replaceitem.reconfigure.util.ColoredAxisQuadGuiElementRenderState;
import net.replaceitem.reconfigure.util.DrawUtil;
import net.replaceitem.reconfigure.util.HueGradientQuadGuiElementRenderState;

public class GradientSlider extends AbstractSliderButton {
    private final Int2IntFunction colorSupplier;

    private boolean isHsv;
    
    public GradientSlider(int x, int y, int width, int height, Component text, double value, Int2IntFunction colorSupplier) {
        super(x, y, width, height, text, value);
        this.colorSupplier = colorSupplier;
        this.updateMessage();
    }
    
    public void setHsv(boolean hsv) {
        isHsv = hsv;
    }

    public void setValue(double value) {
        double d = this.value;
        this.value = Mth.clamp(value, 0.0, 1.0);
        if (d != this.value) {
            this.applyValue();
        }

        this.updateMessage();
    }
    
    public double getValue() {
        return this.value;
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        Minecraft minecraftClient = Minecraft.getInstance();
        context.fill(getX(), getY(), getRight(), getBottom(), CommonColors.BLACK);
        if(ARGB.alpha(colorSupplier.get(0)) < 255 || ARGB.alpha(colorSupplier.get(1)) < 255) {
            context.fill(getX()+1, getY()+1, getRight()-1, getBottom()-1, CommonColors.WHITE);
            DrawUtil.drawCheckerboard(context, getX()+1, getY()+1, getRight()-1, getBottom()-1, 3, CommonColors.LIGHT_GRAY);
        }

        ScreenRectangle colorRect = this.getColorRect();
        if(isHsv) {
            context.guiRenderState.submitGuiElement(HueGradientQuadGuiElementRenderState.draw(
                    context, colorRect.left(), colorRect.top(), colorRect.right(), colorRect.bottom()
            ));
        } else {
            int colorA = colorSupplier.get(0);
            int colorB = colorSupplier.get(1);
            context.guiRenderState.submitGuiElement(ColoredAxisQuadGuiElementRenderState.draw(
                    context, colorRect.left(), colorRect.top(), colorRect.right(), colorRect.bottom(), colorA, colorB, ScreenAxis.HORIZONTAL
            ));
        }

        int handleX = this.getX() + (int) (this.value * (double) (this.width - 8));
        DrawUtil.renderRectOutline(context, handleX, getY(), handleX+8-1, getBottom()-1, ARGB.color(ARGB.as8BitChannel(this.alpha), this.isHovered ? CommonColors.WHITE : CommonColors.LIGHT_GRAY));
        
        int i = this.active ? 16777215 : 10526880;
        this.renderScrollingString(context, minecraftClient.font, 2, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    private ScreenRectangle getColorRect() {
        return new ScreenRectangle(getX()+1, getY()+1, getWidth()-2, getHeight()-2);
    }
    
    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal(String.format("%.1f%%", value*100)));
    }

    @Override
    protected void applyValue() {

    }
}
