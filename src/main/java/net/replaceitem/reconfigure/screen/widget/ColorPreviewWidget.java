package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.replaceitem.reconfigure.util.DrawUtil;


public class ColorPreviewWidget extends AbstractWidget {
    
    private int color;
    
    public ColorPreviewWidget(int x, int y, int height, int width) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(getX(), getY(), getRight(), getBottom(), isHovered ? CommonColors.WHITE : CommonColors.GRAY);
        if(ARGB.alpha(color) < 255) {
            context.fill(getX()+1, getY()+1, getRight()-1, getBottom()-1, CommonColors.WHITE);
            DrawUtil.drawCheckerboard(context, getX()+1, getY()+1, getRight()-1, getBottom()-1, 3, CommonColors.LIGHT_GRAY);
        }
        context.fill(getX()+1, getY()+1, getRight()-1, getBottom()-1, color);
    }
    
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected MutableComponent createNarrationMessage() {
        return Component.literal("#" + Integer.toHexString(color));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        this.defaultButtonNarrationText(builder);
    }

}
