package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class VariableHeightElementListWidget<E extends VariableHeightElementListWidget.Entry<E>> extends ElementListWidget<E> {
    public static final int GAP = 4;
    
    public VariableHeightElementListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
    }
    
    @Nullable
    protected final E getEntryAtPosition(double x, double y) {
        int halfEntryWidth = this.getRowWidth() / 2;
        int centerX = this.getX() + this.width / 2;
        int minX = centerX - halfEntryWidth;
        int maxX = centerX + halfEntryWidth;
        int elementsY = MathHelper.floor(y - (double)this.getY()) - this.headerHeight + (int)this.getScrollAmount() - GAP;
        if(x < (double) minX || x > (double) maxX || elementsY < 0) return null;
        
        for (E child : this.children()) {
            int height = child.getHeight() + GAP;
            elementsY -= height;
            if(elementsY < 0) return child;
        }
        return null;
    }


    @Override
    protected int getMaxPosition() {
        return this.headerHeight + this.children().stream().mapToInt(Entry::getHeight).sum() + GAP * this.getEntryCount();
    }

    @Override
    protected void centerScrollOn(E entry) {
        int scroll = this.itemHeight / 2 - this.height / 2;
        for (E child : this.children()) {
            if(child == entry) break;
            scroll += child.getHeight() + GAP;
        }
        this.setScrollAmount(scroll);
    }

    @Override
    protected void ensureVisible(E entry) {
        int topScrollOfElement = children().stream().takeWhile(e -> e != entry).mapToInt(Entry::getHeight).map(h -> h + GAP).sum() + this.headerHeight + GAP;
        if(topScrollOfElement < getScrollAmount()) {
            this.setScrollAmount(topScrollOfElement);
            return;
        }
        int bottomScrollOfElement = topScrollOfElement + entry.getHeight() + GAP;
        if(bottomScrollOfElement > getScrollAmount() + height) {
            this.setScrollAmount(bottomScrollOfElement - height);
        }
    }

    @Override
    protected void renderList(DrawContext context, int mouseX, int mouseY, float delta) {
        int rowLeft = this.getRowLeft();
        int rowWidth = this.getRowWidth();

        int rowTop = getRowTop(0);
        int index = 0;
        for (E child : children()) {
            int rowBottom = rowTop + child.getHeight();
            if(rowBottom >= this.getY() && rowTop <= this.getBottom()) {
                this.renderEntry(context, mouseX, mouseY, delta, index, rowLeft, rowTop, rowWidth, child.getHeight());
            }
            
            rowTop += child.getHeight() + GAP;
            index++;
        }
    }

    @Override
    public int getRowTop(int index) {
        return this.getY() + GAP - (int)this.getScrollAmount() + this.children().stream().limit(index).mapToInt(Entry::getHeight).map(h -> h + GAP).sum() + this.headerHeight;
    }

    @Override
    public int getRowBottom(int index) {
        return super.getRowTop(index+1);
    }

    // for some reason vanilla adds 2px offset here which breaks interactions. Remove that
    @Override
    public int getRowLeft() {
        return super.getRowLeft() - 2;
    }

    public static abstract class Entry<E extends VariableHeightElementListWidget.Entry<E>> extends ElementListWidget.Entry<E> {
        protected int height;

        public Entry(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }
        
        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            
        }
    }
}
