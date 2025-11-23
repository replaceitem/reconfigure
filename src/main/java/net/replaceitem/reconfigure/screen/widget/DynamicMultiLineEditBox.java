package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.mixin.MultilineTextFieldAccessor;
import net.replaceitem.reconfigure.mixin.MultiLineEditBoxAccessor;

public class DynamicMultiLineEditBox extends MultiLineEditBox {
    /**
     * Width including the scrollbar. The {@link net.minecraft.client.gui.components.AbstractWidget#width}
     * is calculated from that and might be smaller to fit the scrollbar if overflowing.
     */
    protected int totalWidth;
    
    public DynamicMultiLineEditBox(Font textRenderer, int x, int y, int width, int height, Component placeholder, Component message) {
        super(textRenderer, x, y, width, height, placeholder, message, 0xffe0e0e0, true, 0xffd0d0d0, true, true);
        this.setWidth(width);
    }
    
    
    // To fix an ArithmeticException: / by zero crash when the scrollbar's height
    // gets calculated as the exact height. Even when overflows() returns true,
    // the scrollbar height can be 100% of the widgets height because only one padding
    // is accounted for, where it should be two. 
    @Override
    protected int scrollerHeight() {
        int scrollbarHeight = super.scrollerHeight();
        return scrollbarHeight == height ? scrollbarHeight - 1 : scrollbarHeight;
    }

    // Why isn't this the way it's done in vanilla?
    @Override
    public void setSize(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void setWidth(int width) {
        if(this.totalWidth == width) return;
        this.totalWidth = width;
        int scrollbarWidth = scrollbarVisible() ? SCROLLBAR_WIDTH : 0;
        super.setWidth(width - scrollbarWidth);
        MultilineTextFieldAccessor textField = (MultilineTextFieldAccessor) ((MultiLineEditBoxAccessor) this).getTextField();
        textField.setWidth(width - totalInnerPadding() - scrollbarWidth);
        textField.callReflowDisplayLines();
        this.setScrollAmount(this.scrollAmount());
    }
}
