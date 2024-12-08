package net.replaceitem.reconfigure.config.widget.builder;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.PropertyHolder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
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
     * This is run before calling {@link #buildImpl(PropertyHolder)}.
     * Can be used to fill in missing default values.
     */
    protected void preBuild(PropertyHolder<T> property) {
        if(displayName == null) displayName(Text.translatable(property.getId().toTranslationKey(NAMESPACE + ".property")));
    }

    /**
     * This is run after {@link #buildImpl(PropertyHolder)} to add the created Property to the tab.
     */
    protected void postBuild(PropertyHolder<T> property) {
        this.propertyBuildContext.addProperty(property);
    }

    @Override
    public final PropertyImpl<T> build() {
        PropertyHolder<T> property = this.propertyBuilder.build();
        this.preBuild(property);
        PropertyHolder<T> widget = this.buildImpl(property);
        this.postBuild(widget);
        return property.getProperty();
    }

    protected PropertyHolder<T> buildImpl(PropertyHolder<T> property) {
        assert displayName != null;
        assert tooltip != null;
        return property.withWidgetFactory(buildWidgetFactory(new BaseSettings(displayName, tooltip)));
    }
    
    protected abstract ConfigWidgetFactory<T> buildWidgetFactory(BaseSettings baseSettings);

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
