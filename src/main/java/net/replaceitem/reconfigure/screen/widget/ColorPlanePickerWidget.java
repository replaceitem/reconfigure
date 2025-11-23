package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenAxis;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.replaceitem.reconfigure.util.ColoredAxisQuadGuiElementRenderState;
import net.replaceitem.reconfigure.util.DrawUtil;
import net.replaceitem.reconfigure.util.HueGradientQuadGuiElementRenderState;

public class ColorPlanePickerWidget extends AbstractWidget {

    private boolean hasSelection;
    private double colorX;
    private double colorY;

    public ColorPlanePickerWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    public void onColorChanged(float hue, float saturation, float value) {

    }

    public void setHasSelection(boolean hasSelection) {
        this.hasSelection = hasSelection;
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(getX(), getY(), getRight(), getBottom(), CommonColors.LIGHT_GRAY);
        renderHsvGradient(context);
        if (hasSelection) {
            ScreenRectangle colorRect = getColorRect();
            this.renderRectOutline(
                    context,
                    (float) Mth.lerp(colorX, colorRect.left(), colorRect.right()),
                    (float) Mth.lerp(colorY, colorRect.top(), colorRect.bottom()),
                    ARGB.lerp(Mth.clamp((float) colorY * 2 - 1, 0, 1), CommonColors.BLACK, CommonColors.WHITE)
            );
        }
    }

    private void renderRectOutline(GuiGraphics context, float cx, float cy, int color) {
        context.pose().pushMatrix();
        context.pose().translate(cx, cy);
        DrawUtil.renderRectOutline(context, -3, -3, 3, 3, color);
        context.pose().popMatrix();
    }


    private void renderHsvGradient(GuiGraphics context) {
        ScreenRectangle colorRect = getColorRect();
        float yCenter = colorRect.getCenterInAxis(ScreenAxis.VERTICAL);
        context.guiRenderState.submitGuiElement(HueGradientQuadGuiElementRenderState.draw(
            context, colorRect.left(), colorRect.top(), colorRect.right(), colorRect.bottom()
        ));
        context.guiRenderState.submitGuiElement(ColoredAxisQuadGuiElementRenderState.draw(
                context, colorRect.left(), colorRect.top(), colorRect.right(), yCenter, 0xFFFFFFFF, 0x00FFFFFF, ScreenAxis.VERTICAL
        ));
        context.guiRenderState.submitGuiElement(ColoredAxisQuadGuiElementRenderState.draw(
                context, colorRect.left(), yCenter, colorRect.right(), colorRect.bottom(), 0x00000000, 0xFF000000, ScreenAxis.VERTICAL
        ));
    }

    @Override
    public void onClick(MouseButtonEvent click, boolean doubled) {
        if(click.button() == 0) {
            this.setColorAtMouse(click.x(), click.y());
        }
    }

    @Override
    protected void onDrag(MouseButtonEvent click, double offsetX, double offsetY) {
        if(click.button() == 0) {
            this.setColorAtMouse(click.x(), click.y());
        }
    }

    private void setColorAtMouse(double mouseX, double mouseY) {
        ScreenRectangle colorRect = getColorRect();
        this.setColor(
                Math.abs(Mth.clamp(Mth.inverseLerp(mouseX, colorRect.left(), colorRect.right()), 0, 1)),
                Math.abs(Mth.clamp(Mth.inverseLerp(mouseY, colorRect.top(), colorRect.bottom()), 0, 1))
        );
    }

    public void setFromHsv(float hue, float saturation, float value) {
        if (ARGB.as8BitChannel(saturation) == 255 || ARGB.as8BitChannel(value) == 0) {
            setColor(hue, 1.0f - 0.5f * value);
        } else if (ARGB.as8BitChannel(value) == 255) {
            setColor(hue, 0.5f * saturation);
        } else {
            setHasSelection(false);
        }
    }

    public void setColor(double colorX, double colorY) {
        hasSelection = true;
        this.colorX = colorX;
        this.colorY = colorY;
        // copied from shader:
        // vec3 hsv = vec3(color.r, clamp((color.gb-vec2(0.0,1.0)) * vec2(2.0,-2.0), 0.0, 1.0));
        float hue = (float) colorX;
        float saturation = Math.abs(Mth.clamp(((float) colorY) * 2.0f, 0, 1));
        float value = Math.abs(Mth.clamp((((float) colorY) - 1) * -2, 0, 1));
        this.onColorChanged(hue, saturation, value);
    }

    private ScreenRectangle getColorRect() {
        return new ScreenRectangle(getX() + 1, getY() + 1, getWidth() - 2, getHeight() - 2);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {

    }
}
