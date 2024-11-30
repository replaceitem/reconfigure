package net.replaceitem.reconfigure.config.widget.builder;

import net.replaceitem.reconfigure.api.widget.CustomWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.CyclingButtonWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;

import java.util.function.Function;

public class CustomWidgetBuilderImpl<T> extends WidgetBuilderImpl<CyclingButtonWidgetBuilder<T>,T> implements CustomWidgetBuilder<T> {
    
    private final Function<BaseSettings,ConfigWidgetFactory<T>> widgetFactorySupplier;
    
    public CustomWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, T> propertyBuilder, Function<BaseSettings, ConfigWidgetFactory<T>> widgetFactorySupplier) {
        super(propertyBuildContext, propertyBuilder);
        this.widgetFactorySupplier = widgetFactorySupplier;
    }

    @Override
    protected ConfigWidgetFactory<T> buildWidgetFactory(BaseSettings baseSettings) {
        return widgetFactorySupplier.apply(baseSettings);
    }
}
