package net.replaceitem.reconfigure.api;


import java.util.function.Consumer;
import java.util.function.Function;

public interface Bindable<T> {
    /**
     * Gets the current value
     * @return The current value
     */
    T get();

    /**
     * Adds a listener to this bindable. Whenever it changes, this listener will be invoked with the new value.
     * @param listener The listener
     */
    void addListener(Consumer<T> listener);

    /**
     * Removes a listener from this bindable. This must be the exact same instance of the listener you previously added.
     * @param listener The reference to the listener to remove
     */
    void removeListener(Consumer<T> listener);

    /**
     * Creates a new bindable derived with the provided mapper function.
     * @param mapper The mapping function
     * @return The mapped bindable
     * @param <M> The type of the derived bindable
     */
    <M> Bindable<M> map(Function<T,M> mapper);

    /**
     * Adds a listener to this bindable and also immediately emits the current value to the listener.
     * @param listener The listener to add
     */
    void observe(Consumer<T> listener);
}
