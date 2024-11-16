package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.Widget;

public abstract class PositioningEntryWidget extends ElementListWidget.Entry<ConfigWidget> implements Widget {

    protected int x, y, width, height;
    
    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        if(this.x != x || this.y != y || this.width != entryWidth || this.height != entryHeight) {
            this.x = x;
            this.y = y;
            this.width = entryWidth;
            this.height = entryHeight;
            this.refreshPosition();
        }
    }

    protected void refreshPosition() {

    }

    @Override
    public void setX(int x) {
        this.x = x;
        refreshPosition();
    }

    @Override
    public void setY(int y) {
        this.y = y;
        refreshPosition();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public ScreenRect getNavigationFocus() {
        return Widget.super.getNavigationFocus();
    }
}
