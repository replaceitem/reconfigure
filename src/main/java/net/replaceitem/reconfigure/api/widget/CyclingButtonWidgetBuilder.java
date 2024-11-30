package net.replaceitem.reconfigure.api.widget;

import net.minecraft.text.Text;

import java.util.Collection;
import java.util.function.Function;

public interface CyclingButtonWidgetBuilder<T> extends WidgetBuilder<CyclingButtonWidgetBuilder<T>, T> {
    CyclingButtonWidgetBuilder<T> values(Collection<T> values);

    CyclingButtonWidgetBuilder<T> valueToText(Function<T, Text> valueToText);
}
