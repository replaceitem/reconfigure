package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class DynamicEditBox extends EditBox {
    public DynamicEditBox(Font textRenderer, int x, int y, int width, int height, Component text) {
        super(textRenderer, x, y, width, height, text);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        this.setCursorPosition(this.getCursorPosition());
    }

    @Override
    public void setSize(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }
}
