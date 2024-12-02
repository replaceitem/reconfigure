package net.replaceitem.reconfigure.config.widget.builder;

import net.replaceitem.reconfigure.api.widget.AbstractTextFieldWidgetBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTextFieldWidgetBuilderImpl<SELF extends AbstractTextFieldWidgetBuilder<SELF>> extends WidgetBuilderImpl<SELF, String> implements AbstractTextFieldWidgetBuilder<SELF> {
    
    @Nullable protected String placeholder;
    
    public AbstractTextFieldWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, String> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }
    
    @Override
    public SELF placeholder(String placeholder) {
        this.placeholder = placeholder;
        return self();
    }

}
