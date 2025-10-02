package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.Widget;

public abstract class PositioningEntryWidget<E extends ElementListWidget.Entry<E>> extends ElementListWidget.Entry<E> implements Widget {

    @Override
    public void setY(int y) {
        super.setY(y);
        this.refreshPosition();
    }

    public void setContentHeight(int height) {
        this.setHeight(height + 2 * PADDING);
    }

    public void refreshPosition() {

    }
}
