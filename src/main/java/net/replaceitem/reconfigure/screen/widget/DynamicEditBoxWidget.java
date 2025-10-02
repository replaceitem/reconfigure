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
        super(textRenderer, x, y, width, height, placeholder, message, 0xffe0e0e0, true, 0xffd0d0d0, true, true);
        this.setWidth(width);
    }
    
    
    // To fix an ArithmeticException: / by zero crash when the scrollbar's height
    // gets calculated as the exact height. Even when overflows() returns true,
    // the scrollbar height can be 100% of the widgets height because only one padding
    // is accounted for, where it should be two. 
    @Override
    protected int getScrollbarThumbHeight() {
        int scrollbarHeight = super.getScrollbarThumbHeight();
        return scrollbarHeight == height ? scrollbarHeight - 1 : scrollbarHeight;
    }

    // Why isn't this the way it's done in vanilla?
    @Override
    public void setDimensions(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void setWidth(int width) {
        if(this.totalWidth == width) return;
        this.totalWidth = width;
        int scrollbarWidth = overflows() ? SCROLLBAR_WIDTH : 0;
        super.setWidth(width - scrollbarWidth);
        EditBoxAccessor editBox = (EditBoxAccessor) ((EditBoxWidgetAccessor) this).getEditBox();
        editBox.setWidth(width - getPadding() - scrollbarWidth);
        editBox.callRewrap();
        this.setScrollY(this.getScrollY());
    }
}
