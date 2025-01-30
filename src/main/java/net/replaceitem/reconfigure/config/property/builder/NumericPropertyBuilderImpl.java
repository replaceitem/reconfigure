package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.NumericPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import org.jetbrains.annotations.Nullable;

public abstract class NumericPropertyBuilderImpl<SELF extends NumericPropertyBuilder<SELF, T>, T extends Number> extends PropertyBuilderImpl<SELF, T> implements NumericPropertyBuilder<SELF, T> {
    
    @Nullable protected T min;
    @Nullable protected T max;

    public NumericPropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id, T defaultValue) {
        super(propertyBuildContext, id, defaultValue);
    }
    
    
    @Override
    public SELF min(T min) {
        this.min = min;
        return self();
    }
    
    @Override
    public SELF max(T max) {
        this.max = max;
        return self();
    }
    
    @Override
    public SELF range(T min, T max) {
        return min(min).max(max);
    }
}
