package net.replaceitem.reconfigure.config.widget.builder;

import net.replaceitem.reconfigure.api.widget.ChipListWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.ChipListConfigWidget;

import java.util.List;

public class ChipListWidgetBuilderImpl extends WidgetBuilderImpl<ChipListWidgetBuilder, List<String>> implements ChipListWidgetBuilder {
    
    private boolean chipsEditable = true;
    
    public ChipListWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, List<String>> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }
    
    @Override
    public ChipListWidgetBuilderImpl chipsEditable(boolean editable) {
        this.chipsEditable = editable;
        return this;
    }
    
    @Override
    protected ConfigWidgetFactory<List<String>> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) -> new ChipListConfigWidget(parent, property, baseSettings, chipsEditable);
    }
}
