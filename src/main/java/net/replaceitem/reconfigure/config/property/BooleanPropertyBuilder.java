package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.config.widget.CheckboxWidgetBuilder;
import net.replaceitem.reconfigure.config.widget.CyclingButtonWidgetBuilder;

import java.util.function.Consumer;

public class BooleanPropertyBuilder extends PropertyBuilder<BooleanPropertyBuilder, Boolean> {
    public BooleanPropertyBuilder(Consumer<Property<Boolean>> propertyConsumer, Identifier id) {
        super(propertyConsumer, id);
        defaultValue = false;
    }
    
    public CheckboxWidgetBuilder asCheckbox() {
        return new CheckboxWidgetBuilder(this);
    }
    public CyclingButtonWidgetBuilder<Boolean> asToggleButton() {
        return CyclingButtonWidgetBuilder.createBoolean(this);
    }
}
