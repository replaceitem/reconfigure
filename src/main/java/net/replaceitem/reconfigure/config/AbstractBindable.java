package net.replaceitem.reconfigure.config;

import net.replaceitem.reconfigure.api.Bindable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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

    @Override
    public <M> Bindable<M> map(Function<T, M> mapper) {
        return new MappedBindable<>(mapper, this);
    }

    @Override
    public void observe(Consumer<T> listener) {
        listener.accept(get());
        this.addListener(listener);
    }

    protected void callListeners(T value) {
        this.listeners.forEach(tConsumer -> tConsumer.accept(value));
    }
    
    protected boolean hasListeners() {
        return !this.listeners.isEmpty();
    }
}
