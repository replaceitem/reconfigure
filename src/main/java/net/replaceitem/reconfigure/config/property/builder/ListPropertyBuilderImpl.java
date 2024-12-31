package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.ListPropertyBuilder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.serialization.TypeAdapter;
import net.replaceitem.reconfigure.config.widget.builder.ChipListWidgetBuilderImpl;

import java.util.List;

public class ListPropertyBuilderImpl extends PropertyBuilderImpl<ListPropertyBuilder, List<String>> implements ListPropertyBuilder {
    
    public ListPropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id, List<String> defaultValue) {
        super(propertyBuildContext, id, defaultValue);
    }

    @Override
    protected TypeAdapter<List<String>, ?> getTypeAdapter() {
        return TypeAdapter.LIST;
    }
    
    @Override
    public ChipListWidgetBuilderImpl asChipList() {
        return new ChipListWidgetBuilderImpl(propertyBuildContext, this);
    }
}
