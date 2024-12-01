package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.StringPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Caster;
import net.replaceitem.reconfigure.config.widget.builder.EditBoxWidgetBuilderImpl;
import net.replaceitem.reconfigure.config.widget.builder.TextFieldWidgetBuilderImpl;

public class StringPropertyBuilderImpl extends PropertyBuilderImpl<StringPropertyBuilder, String> implements StringPropertyBuilder {
    public StringPropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id) {
        super(propertyBuildContext, id);
        defaultValue = "";
    }

    @Override
    protected Caster<String> getCaster() {
        return Caster.STRING;
    }

    @Override
    public TextFieldWidgetBuilderImpl asTextField() {
        return new TextFieldWidgetBuilderImpl(propertyBuildContext, this);
    }
    
    @Override
    public EditBoxWidgetBuilderImpl asEditBox() {
        return new EditBoxWidgetBuilderImpl(propertyBuildContext, this);
    }
}
