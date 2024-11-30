package net.replaceitem.reconfigure.api.widget;

public interface SliderWidgetBuilder<SELF extends SliderWidgetBuilder<SELF, T>, T extends Number & Comparable<T>> extends WidgetBuilder<SELF, T> {
    SELF min(T min);

    SELF max(T max);

    default SELF range(T min, T max) {
        return min(min).max(max);
    }

    SELF step(T step);
}
