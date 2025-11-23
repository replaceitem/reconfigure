package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.TooltippedStringWidget;

public class HeadlineConfigWidget extends ConfigWidget {
    
    private final TooltippedStringWidget stringWidget;

    public static final int HEIGHT = 30;

    public HeadlineConfigWidget(ConfigWidgetList listWidget, Component text) {
        super(listWidget, HEIGHT);
        this.stringWidget = new TooltippedStringWidget(text, this.parent.getTextRenderer());
        this.children.add(this.stringWidget);
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        int padding = (this.getContentHeight() - this.parent.getTextRenderer().lineHeight) / 2;
        this.stringWidget.setMaxWidth(this.getContentWidth() - 2 * padding);
        // For some reason, setMaxWidth doesn't set maxWidthDirty to true, even though it would need to be.
        // Here the message is re-set to set it dirty.
        this.stringWidget.setMessage(this.stringWidget.getMessage());
        this.stringWidget.setHeight(this.getContentHeight() - 2 * padding);
        this.stringWidget.setY(this.getContentY() + padding);
        this.stringWidget.setX(this.getContentXMiddle() - this.stringWidget.getWidth() / 2);
    }
}
