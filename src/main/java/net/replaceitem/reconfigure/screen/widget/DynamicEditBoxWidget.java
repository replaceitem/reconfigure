package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.mixin.EditBoxAccessor;
import net.replaceitem.reconfigure.mixin.EditBoxWidgetAccessor;

public class DynamicEditBoxWidget extends EditBoxWidget {
    /**
     * Width including the scrollbar. The {@link net.minecraft.client.gui.widget.ClickableWidget#width}
     * is calculated from that and might be smaller to fit the scrollbar if overflowing.
     */
    protected int totalWidth;
    
    public DynamicEditBoxWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text placeholder, Text message) {
        super(textRenderer, x, y, width, height, placeholder, message);
        this.setWidth(width);
        this.setChangeListener(s -> setWidth(totalWidth));
    }
    
    // Why isn't this the way it's done in vanilla?
    @Override
    public void setDimensions(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void setWidth(int width) {
        this.totalWidth = width;
        int scrollbarWidth = overflows() ? getScrollerWidth() : 0;
        super.setWidth(width - scrollbarWidth);
        ((EditBoxAccessor) ((EditBoxWidgetAccessor) this).getEditBox()).setWidth(width - getPaddingDoubled() - scrollbarWidth);
    }
}
