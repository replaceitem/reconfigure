package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.BooleanPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.Caster;
import net.replaceitem.reconfigure.config.widget.builder.CheckboxWidgetBuilderImpl;
import net.replaceitem.reconfigure.config.widget.builder.CyclingButtonWidgetBuilderImpl;

public class BooleanPropertyBuilderImpl extends PropertyBuilderImpl<BooleanPropertyBuilder, Boolean> implements BooleanPropertyBuilder {
    public BooleanPropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id) {
        super(propertyBuildContext, id);
        defaultValue = false;
    }

    @Override
    protected Caster<Boolean> getCaster() {
        return Caster.BOOLEAN;
    }

    @Override
    public CheckboxWidgetBuilderImpl asCheckbox() {
        return new CheckboxWidgetBuilderImpl(propertyBuildContext, this);
    }
    @Override
    public CyclingButtonWidgetBuilderImpl<Boolean> asToggleButton() {
        return CyclingButtonWidgetBuilderImpl.createBoolean(propertyBuildContext, this);
    }
}
