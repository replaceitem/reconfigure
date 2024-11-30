package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.CustomWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;

import java.util.function.Function;

public interface PropertyBuilder<SELF extends PropertyBuilder<SELF, T>, T> {
    SELF defaultValue(T defaultValue);

    CustomWidgetBuilder<T> asCustomWidget(Function<BaseSettings, ConfigWidgetFactory<T>> widgetFactorySupplier);
}
