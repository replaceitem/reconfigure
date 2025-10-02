package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;

public class VariableHeightElementListWidget<E extends VariableHeightElementListWidget.Entry<E>> extends ElementListWidget<E> {
    public VariableHeightElementListWidget(MinecraftClient minecraftClient, int width, int height, int y) {
        super(minecraftClient, width, height, y, 30); // dummy item height, no longer used here, just for scroll speed
    }

    @Override
    protected int addEntry(E entry) {
        return this.addEntry(entry, entry.getHeight());
    }

    @Override
    protected void addEntryToTop(E entry) {
        this.addEntryToTop(entry, this.itemHeight);
    }

    public void reposition() {
        // recalculates all element positions
        this.position(this.width, this.height, this.getX(), this.getY());
    }
}
