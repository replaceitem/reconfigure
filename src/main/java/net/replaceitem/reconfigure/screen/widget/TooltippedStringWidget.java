package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.mixin.StringWidgetAccessor;
import org.jetbrains.annotations.Nullable;

public class TooltippedStringWidget extends StringWidget {

    @Nullable
    private Component additionalTooltip;
    public TooltippedStringWidget(Component message, Font textRenderer) {
        super(message, textRenderer);
    }

    public boolean isOverflowing() {
        Component text = this.getMessage();
        Font textRenderer = this.getFont();
        int maxWidth = ((StringWidgetAccessor) this).getMaxWidth();
        int width = maxWidth > 0 ? maxWidth : this.getWidth();
        int textWidth = textRenderer.width(text);
        return textWidth > width;
    }

    @Override
    public StringWidget setMaxWidth(int width, TextOverflow textOverflow) {
        super.setMaxWidth(width, textOverflow);
        this.createTooltip();
        return this;
    }

    @Override
    public void setMessage(Component message) {
        super.setMessage(message);
        this.createTooltip();
    }

    public void setAdditionalTooltip(@Nullable Component tooltip) {
        this.additionalTooltip = tooltip;
        this.createTooltip();
    }

    protected void createTooltip() {
        if(isOverflowing()) {
            this.setTooltip(Tooltip.create(
                    this.additionalTooltip == null ?
                            this.getMessage() :
                            Component.empty().append(getMessage()).append("\n").append(this.additionalTooltip)
            ));
        } else {
            this.setTooltip(this.additionalTooltip == null ? null : Tooltip.create(this.additionalTooltip));
        }
    }
}
