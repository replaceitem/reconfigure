package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.replaceitem.reconfigure.util.DrawUtil;


public class ColorPreviewWidget extends ClickableWidget {
    
    private int color;
    
    public ColorPreviewWidget(int x, int y, int height, int width) {
        super(x, y, width, height, Text.empty());
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(getX(), getY(), getRight(), getBottom(), hovered ? Colors.WHITE : Colors.GRAY);
        if(ColorHelper.getAlpha(color) < 255) {
            context.fill(getX()+1, getY()+1, getRight()-1, getBottom()-1, Colors.WHITE);
            DrawUtil.drawCheckerboard(context, getX()+1, getY()+1, getRight()-1, getBottom()-1, 3, Colors.LIGHT_GRAY);
        }
        context.fill(getX()+1, getY()+1, getRight()-1, getBottom()-1, color);
    }
    
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected MutableText getNarrationMessage() {
        return Text.literal("#" + Integer.toHexString(color));
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }

}
