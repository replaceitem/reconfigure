package net.replaceitem.reconfigure.api.property;

public interface NumericPropertyBuilder<SELF extends NumericPropertyBuilder<SELF, T>, T extends Number> extends PropertyBuilder<SELF, T> {
    SELF min(T min);

    SELF max(T max);

    SELF range(T min, T max);
}
