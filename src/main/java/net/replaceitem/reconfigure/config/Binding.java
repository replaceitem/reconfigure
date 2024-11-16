package net.replaceitem.reconfigure.config;

public class Binding<T> {
    protected T value;
    
    public T get() {
        return value;
    }
    
    public void set(T value) {
        this.value = value;
    }
}
