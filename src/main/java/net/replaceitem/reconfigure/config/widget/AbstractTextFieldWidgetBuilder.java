package net.replaceitem.reconfigure.config.widget;

import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.config.WidgetBuilder;

public abstract class AbstractTextFieldWidgetBuilder<SELF extends AbstractTextFieldWidgetBuilder<SELF>> extends WidgetBuilder<SELF, String> {
    
    protected String placeholder;
    
    public AbstractTextFieldWidgetBuilder(PropertyBuilder<?, String> propertyBuilder) {
        super(propertyBuilder);
    }
    
    public SELF placeholder(String placeholder) {
        this.placeholder = placeholder;
        return self();
    }

}
