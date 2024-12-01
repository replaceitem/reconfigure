package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class DynamicTextFieldWidget extends TextFieldWidget {
    public DynamicTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(textRenderer, x, y, width, height, text);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        this.setSelectionStart(this.getCursor());
    }

    @Override
    public void setDimensions(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }
}
