package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.config.PropertyBuilder;

import java.util.function.Consumer;

public class NumericPropertyBuilder<SELF extends NumericPropertyBuilder<SELF, T>, T extends Number> extends PropertyBuilder<SELF, T> {
    
    protected T min;
    protected T max;

    public NumericPropertyBuilder(Consumer<Property<T>> propertyConsumer, Identifier id) {
        super(propertyConsumer, id);
    }
    
    
    public SELF min(T min) {
        this.min = min;
        return self();
    }
    
    public SELF max(T max) {
        this.max = max;
        return self();
    }
    
    public SELF range(T min, T max) {
        return min(min).max(max);
    }


}
