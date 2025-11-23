package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.layouts.LayoutElement;

public abstract class PositioningSelectionListEntry<E extends ContainerObjectSelectionList.Entry<E>> extends ContainerObjectSelectionList.Entry<E> implements LayoutElement {

    @Override
    public void setY(int y) {
        super.setY(y);
        this.refreshPosition();
    }

    public void setContentHeight(int height) {
        this.setHeight(height + 2 * CONTENT_PADDING);
    }

    public void refreshPosition() {

    }
}
