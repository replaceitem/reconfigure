package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public class HeadlineConfigWidget extends ConfigWidget {
    
    private final Text text;
    
    public HeadlineConfigWidget(ConfigWidgetList listWidget, Text text) {
        super(listWidget);
        this.text = text;
    }

    @Override
    public void renderWidgets(DrawContext context, int index, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        this.renderName(context, index, mouseX, mouseY, hovered, tickDelta);
    }

    protected void renderName(DrawContext context, int index, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int textPadding = height / 2 - this.parent.getTextRenderer().fontHeight / 2;
        int textY = y + textPadding;
        context.drawCenteredTextWithShadow(this.parent.getTextRenderer(), this.text, x + width / 2, textY, Colors.WHITE);
    }

    @Override
    protected void onSave() {}
}
