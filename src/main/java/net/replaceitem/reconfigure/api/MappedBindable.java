package net.replaceitem.reconfigure.api;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Function;

public class MappedBindable<T,U> extends AbstractBindable<T> {

    @Nullable
    private T cached;
    private boolean invalid = true;
    private final Function<U,T> mapper;
    private final Bindable<U> upstream;
    
    MappedBindable(Function<U, T> mapper, Bindable<U> upstream) {
        this.mapper = mapper;
        this.upstream = upstream;
        this.upstream.addListener(u -> {
            invalid = true;
            if(this.hasListeners()) {
                this.callListeners(get());
            }
        });
    }
    
    @UnknownNullability
    @Override
    public T get() {
        if(this.invalid) this.cached = this.mapper.apply(this.upstream.get());
        this.invalid = false;
        return this.cached;
    }
}
