package net.replaceitem.reconfigure.screen;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.Config;
import org.jetbrains.annotations.Nullable;

public class ConfigScreen extends Screen {
    @Nullable private final Screen parent;
    private final Config config;
    private TabNavigationWidget tabNavigation;
    private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);
    private final ThreePartsLayoutWidget threePartsLayoutWidget = new ThreePartsLayoutWidget(this);
    
    public ConfigScreen(Config config, Screen parent) {
        super(config.getTitle());
        this.config = config;
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.tabNavigation = TabNavigationWidget.builder(this.tabManager, this.width)
                .tabs(this.config.getTabs().stream()
                        .map(configTab -> new ConfigScreenTab(configTab, client))
                        .toArray(Tab[]::new))
                .build();
        
        this.tabNavigation.selectTab(0, false);

        DirectionalLayoutWidget directionalLayoutWidget = this.threePartsLayoutWidget.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("reconfigure.cancel"), button -> close()).build());
        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("reconfigure.save"), button -> System.out.println("Saving")).build());

        this.threePartsLayoutWidget.forEachChild(this::addDrawableChild);

        this.addDrawableChild(this.tabNavigation);
        refreshWidgetPositions();
    }

    @Override
    public void close() {
        if(this.client != null) this.client.setScreen(parent);
    }

    @Override
    protected void refreshWidgetPositions() {
        if (this.tabNavigation != null) {
            this.tabNavigation.setWidth(this.width);
            this.tabNavigation.init();
            int tabHeight = this.tabNavigation.getNavigationFocus().getBottom();
            ScreenRect screenRect = new ScreenRect(0, tabHeight, this.width, this.height - this.threePartsLayoutWidget.getFooterHeight() - tabHeight);
            this.tabManager.setTabArea(screenRect);
            this.threePartsLayoutWidget.setHeaderHeight(tabHeight);
            this.threePartsLayoutWidget.refreshPositions();
        }
    }
}
