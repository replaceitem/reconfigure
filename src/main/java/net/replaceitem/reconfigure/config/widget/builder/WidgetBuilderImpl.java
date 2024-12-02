package net.replaceitem.reconfigure.config.widget.builder;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.config.widget.Widget;
import org.jetbrains.annotations.Nullable;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public abstract class WidgetBuilderImpl<SELF extends WidgetBuilder<SELF, T>, T> implements WidgetBuilder<SELF, T> {
    private final PropertyBuildContext propertyBuildContext;
    protected final PropertyBuilderImpl<?, T> propertyBuilder;
    @Nullable protected Text displayName;
    @Nullable protected Tooltip tooltip;

    protected WidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, T> propertyBuilder) {
        this.propertyBuildContext = propertyBuildContext;
        this.propertyBuilder = propertyBuilder;
    }


    @Override
    public SELF displayName(Text displayName) {
        this.displayName = displayName;
        return self();
    }
    
    @Override
    public SELF tooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
        return self();
    }
    @Override
    public SELF tooltip(Text tooltipText) {
        return tooltip(Tooltip.of(tooltipText));
    }

    /**
     * This is run before calling {@link #buildImpl(PropertyImpl)}.
     * Can be used to fill in missing default values.
     */
    protected void preBuild(PropertyImpl<T> property) {
        if(displayName == null) displayName(Text.translatable(property.getId().toTranslationKey(NAMESPACE + ".property")));
    }

    /**
     * This is run after {@link #buildImpl(PropertyImpl)} to add the created Property to the tab.
     */
    protected void postBuild(Widget<T> widget) {
        this.propertyBuildContext.addWidget(widget);
    }

    @Override
    public final PropertyImpl<T> build() {
        PropertyImpl<T> property = this.propertyBuilder.build();
        this.preBuild(property);
        Widget<T> widget = this.buildImpl(property);
        this.postBuild(widget);
        return property;
    }

    protected Widget<T> buildImpl(PropertyImpl<T> property) {
        assert displayName != null;
        assert tooltip != null;
        return new Widget<>(property, buildWidgetFactory(new BaseSettings(displayName, tooltip)));
    }
    
    protected abstract ConfigWidgetFactory<T> buildWidgetFactory(BaseSettings baseSettings);

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
