package net.replaceitem.reconfigure.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractBindable<T> implements Bindable<T> {
    
    private final List<Consumer<T>> listeners = new ArrayList<>(0);

    @Override
    public void addListener(Consumer<T> listener) {
        this.listeners.add(listener);
    }
    
    @Override
    public void removeListener(Consumer<T> listener) {
        this.listeners.remove(listener);
    }
    
    protected void callListeners(T value) {
        this.listeners.forEach(tConsumer -> tConsumer.accept(value));
    }
    
    protected boolean hasListeners() {
        return !this.listeners.isEmpty();
    }
}
