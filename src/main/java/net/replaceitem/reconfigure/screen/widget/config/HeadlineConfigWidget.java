package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.TooltippedTextWidget;

public class HeadlineConfigWidget extends ConfigWidget {
    
    private final TooltippedTextWidget textWidget;

    public static final int HEIGHT = 30;

    public HeadlineConfigWidget(ConfigWidgetList listWidget, Text text) {
        super(listWidget, HEIGHT);
        this.textWidget = new TooltippedTextWidget(text, this.parent.getTextRenderer());
        this.children.add(this.textWidget);
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        int padding = (this.getContentHeight() - this.parent.getTextRenderer().fontHeight) / 2;
        this.textWidget.setMaxWidth(this.getContentWidth() - 2 * padding);
        this.textWidget.setHeight(this.getContentHeight() - 2 * padding);
        this.textWidget.setY(this.getContentY() + padding);
        this.textWidget.setX(this.getContentMiddleX() - this.textWidget.getWidth() / 2);
    }
}
