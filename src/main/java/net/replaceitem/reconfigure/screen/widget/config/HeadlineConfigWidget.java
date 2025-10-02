package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public class HeadlineConfigWidget extends ConfigWidget {
    
    private final Text text;
    
    public HeadlineConfigWidget(ConfigWidgetList listWidget, Text text) {
        super(listWidget, 30);
        this.text = text;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
        super.render(context, mouseX, mouseY, hovered, deltaTicks);
        this.renderName(context);
    }

    protected void renderName(DrawContext context) {
        int textY = this.getContentMiddleY() - (this.parent.getTextRenderer().fontHeight / 2);
        context.drawCenteredTextWithShadow(this.parent.getTextRenderer(), this.text, this.getContentMiddleX(), textY, Colors.WHITE);
    }
}
