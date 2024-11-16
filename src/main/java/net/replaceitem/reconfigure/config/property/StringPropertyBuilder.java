package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.config.widget.TextFieldWidgetBuilder;

import java.util.function.Consumer;

public class StringPropertyBuilder extends PropertyBuilder<StringPropertyBuilder, String> {
    public StringPropertyBuilder(Consumer<Property<String>> propertyConsumer, Identifier id) {
        super(propertyConsumer, id);
        defaultValue = "";
    }

    public TextFieldWidgetBuilder asTextField() {
        return new TextFieldWidgetBuilder(this);
    }
}
