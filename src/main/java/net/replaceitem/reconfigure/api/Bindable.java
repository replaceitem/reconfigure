package net.replaceitem.reconfigure.api;


import java.util.function.Consumer;
import java.util.function.Function;

public interface Bindable<T> {
    T get();
    void addListener(Consumer<T> listener);
    void removeListener(Consumer<T> listener);
    <M> Bindable<M> map(Function<T,M> mapper);

    /**
     * Adds a listener to this bindable and immediately emits the current value to the listener.
     */
    void observe(Consumer<T> listener);
}
