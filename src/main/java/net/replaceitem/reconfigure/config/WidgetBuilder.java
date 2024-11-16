package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public abstract class WidgetBuilder<SELF extends WidgetBuilder<SELF, T>, T> {
    
    protected Text displayName;
    protected final PropertyBuilder<?, T> propertyBuilder;

    protected WidgetBuilder(PropertyBuilder<?, T> propertyBuilder) {
        this.propertyBuilder = propertyBuilder;
    }


    public SELF displayName(Text displayName) {
        this.displayName = displayName;
        return self();
    }
    
    protected Identifier getPropertyId() {
        return this.propertyBuilder.id;
    }

    /**
     * This is run before calling {@link #buildImpl()}.
     * Can be used to fill in missing default values.
     */
    protected void preBuild() {
        if(displayName == null) displayName(Text.translatable(getPropertyId().toTranslationKey(NAMESPACE + ".property")));
    }

    /**
     * This is run after {@link #buildImpl()} to add the created Property to the tab.
     */
    protected void postBuild(Property<T> property) {
    }

    public final Property<T> build() {
        this.preBuild();
        Property<T> property = this.buildImpl();
        this.postBuild(property);
        return property;
    }

    protected Property<T> buildImpl() {
        return propertyBuilder.asCustomWidget(buildWidgetFactory());
    }
    
    protected abstract ConfigWidgetFactory<T> buildWidgetFactory();
    
    public record Context() {
        
    }

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
