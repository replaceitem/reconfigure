package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.navigation.NavigationAxis;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.replaceitem.reconfigure.util.ColoredAxisQuadGuiElementRenderState;
import net.replaceitem.reconfigure.util.DrawUtil;
import net.replaceitem.reconfigure.util.HueGradientQuadGuiElementRenderState;

public class ColorPlanePickerWidget extends ClickableWidget {

    private boolean hasSelection;
    private double colorX;
    private double colorY;

    public ColorPlanePickerWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty());
    }

    public void onColorChanged(float hue, float saturation, float value) {

    }

    public void setHasSelection(boolean hasSelection) {
        this.hasSelection = hasSelection;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(getX(), getY(), getRight(), getBottom(), Colors.LIGHT_GRAY);
        renderHsvGradient(context);
        if (hasSelection) {
            ScreenRect colorRect = getColorRect();
            this.renderRectOutline(
                    context,
                    (float) MathHelper.lerp(colorX, colorRect.getLeft(), colorRect.getRight()),
                    (float) MathHelper.lerp(colorY, colorRect.getTop(), colorRect.getBottom()),
                    ColorHelper.lerp(MathHelper.clamp((float) colorY * 2 - 1, 0, 1), Colors.BLACK, Colors.WHITE)
            );
        }
    }

    private void renderRectOutline(DrawContext context, float cx, float cy, int color) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(cx, cy);
        DrawUtil.renderRectOutline(context, -3, -3, 3, 3, color);
        context.getMatrices().popMatrix();
    }


    private void renderHsvGradient(DrawContext context) {
        ScreenRect colorRect = getColorRect();
        float yCenter = colorRect.getCenter(NavigationAxis.VERTICAL);
        context.state.addSimpleElement(HueGradientQuadGuiElementRenderState.draw(
            context, colorRect.getLeft(), colorRect.getTop(), colorRect.getRight(), colorRect.getBottom()
        ));
        context.state.addSimpleElement(ColoredAxisQuadGuiElementRenderState.draw(
                context, colorRect.getLeft(), colorRect.getTop(), colorRect.getRight(), yCenter, 0xFFFFFFFF, 0x00FFFFFF, NavigationAxis.VERTICAL
        ));
        context.state.addSimpleElement(ColoredAxisQuadGuiElementRenderState.draw(
                context, colorRect.getLeft(), yCenter, colorRect.getRight(), colorRect.getBottom(), 0x00000000, 0xFF000000, NavigationAxis.VERTICAL
        ));
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setColorAtMouse(mouseX, mouseY);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.setColorAtMouse(mouseX, mouseY);
    }

    private void setColorAtMouse(double mouseX, double mouseY) {
        ScreenRect colorRect = getColorRect();
        this.setColor(
                Math.abs(MathHelper.clamp(MathHelper.getLerpProgress(mouseX, colorRect.getLeft(), colorRect.getRight()), 0, 1)),
                Math.abs(MathHelper.clamp(MathHelper.getLerpProgress(mouseY, colorRect.getTop(), colorRect.getBottom()), 0, 1))
        );
    }

    public void setFromHsv(float hue, float saturation, float value) {
        if (ColorHelper.channelFromFloat(saturation) == 255 || ColorHelper.channelFromFloat(value) == 0) {
            setColor(hue, 1.0f - 0.5f * value);
        } else if (ColorHelper.channelFromFloat(value) == 255) {
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
        float saturation = Math.abs(MathHelper.clamp(((float) colorY) * 2.0f, 0, 1));
        float value = Math.abs(MathHelper.clamp((((float) colorY) - 1) * -2, 0, 1));
        this.onColorChanged(hue, saturation, value);
    }

    private ScreenRect getColorRect() {
        return new ScreenRect(getX() + 1, getY() + 1, getWidth() - 2, getHeight() - 2);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
