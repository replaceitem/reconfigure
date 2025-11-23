package net.replaceitem.reconfigure.screen.widget.layout;

import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LayoutElement;

import java.util.function.Consumer;

/**
 * Helper wrapper widget that always contains one wrapper widget. This is used to the widget can be swapped out or
 * re-created. Things like GridWidget does not support removing widgets from it, so to do so, a new one needs to be
 * created and re-filled. If the grid widget is contained in another LayoutWidget however, it cannot be removed from
 * that for re-creation. This SocketWidget is used as a way to keep itself in an outer grid, but replace the
 * LayoutWidget within.
 */
public class SocketWidget<T extends Layout> implements Layout {
    private T inner;

    public SocketWidget(T inner) {
        this.inner = inner;
    }

    public T getInner() {
        return inner;
    }

    public void setInner(T inner) {
        this.inner = inner;
    }

    @Override
    public int getX() {
        return inner.getX();
    }

    @Override
    public int getY() {
        return inner.getY();
    }

    @Override
    public int getWidth() {
        return inner.getWidth();
    }

    @Override
    public int getHeight() {
        return inner.getHeight();
    }

    @Override
    public void setX(int x) {
        inner.setX(x);
    }

    @Override
    public void setY(int y) {
        inner.setY(y);
    }

    @Override
    public void arrangeElements() {
        inner.arrangeElements();
    }

    @Override
    public void visitChildren(Consumer<LayoutElement> consumer) {
        inner.visitChildren(consumer);
    }
}
