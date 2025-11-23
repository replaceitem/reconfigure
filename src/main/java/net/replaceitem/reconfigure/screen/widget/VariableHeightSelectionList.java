package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public class VariableHeightSelectionList<E extends VariableHeightSelectionList.Entry<E>> extends ContainerObjectSelectionList<E> {
    public VariableHeightSelectionList(Minecraft minecraftClient, int width, int height, int y) {
        super(minecraftClient, width, height, y, 30); // dummy item height, no longer used here, just for scroll speed
    }

    @Override
    protected int addEntry(E entry) {
        return this.addEntry(entry, entry.getHeight());
    }

    @Override
    protected void addEntryToTop(E entry) {
        this.addEntryToTop(entry, this.defaultEntryHeight);
    }

    public void reposition() {
        // recalculates all element positions
        this.updateSizeAndPosition(this.width, this.height, this.getX(), this.getY());
    }
}
