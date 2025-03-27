package net.replaceitem.reconfigure.config.widget.builder;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.widget.AbstractTextFieldWidgetBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import org.jetbrains.annotations.Nullable;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public abstract class AbstractTextFieldWidgetBuilderImpl<SELF extends AbstractTextFieldWidgetBuilder<SELF>> extends WidgetBuilderImpl<SELF, String> implements AbstractTextFieldWidgetBuilder<SELF> {
    
    @Nullable protected Text placeholder;
    
    public AbstractTextFieldWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, String> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }
    
    @Override
    public SELF placeholder(Text placeholder) {
        this.placeholder = placeholder;
        return self();
    }

    @Override
    public SELF placeholder() {
        this.placeholder = Text.translatable(propertyBuilder.getId().toTranslationKey(NAMESPACE + ".property", "placeholder"));
        return self();
    }
}
