package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.mixin.TextWidgetAccessor;
import org.jetbrains.annotations.Nullable;

public class TooltippedTextWidget extends TextWidget {

    @Nullable
    private Text additionalTooltip;
    public TooltippedTextWidget(Text message, TextRenderer textRenderer) {
        super(message, textRenderer);
    }

    public boolean isOverflowing() {
        Text text = this.getMessage();
        TextRenderer textRenderer = this.getTextRenderer();
        int maxWidth = ((TextWidgetAccessor) this).getMaxWidth();
        int width = maxWidth > 0 ? maxWidth : this.getWidth();
        int textWidth = textRenderer.getWidth(text);
        return textWidth > width;
    }

    @Override
    public TextWidget setMaxWidth(int width, TextOverflow textOverflow) {
        super.setMaxWidth(width, textOverflow);
        this.createTooltip();
        return this;
    }

    @Override
    public void setMessage(Text message) {
        super.setMessage(message);
        this.createTooltip();
    }

    public void setAdditionalTooltip(@Nullable Text tooltip) {
        this.additionalTooltip = tooltip;
        this.createTooltip();
    }

    protected void createTooltip() {
        if(isOverflowing()) {
            this.setTooltip(Tooltip.of(
                    this.additionalTooltip == null ?
                            this.getMessage() :
                            Text.empty().append(getMessage()).append("\n").append(this.additionalTooltip)
            ));
        } else {
            this.setTooltip(Tooltip.of(this.additionalTooltip));
        }
    }
}
