package net.replaceitem.reconfigure.screen;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.ConfigImpl;
import org.jetbrains.annotations.Nullable;

public class ConfigScreen extends Screen {
    @Nullable private final Screen parent;
    private final ConfigImpl config;
    private TabNavigationWidget tabNavigation;
    private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);
    private final ThreePartsLayoutWidget threePartsLayoutWidget = new ThreePartsLayoutWidget(this);
    private ConfigScreenTab[] tabs;

    public ConfigScreen(ConfigImpl config, @Nullable Screen parent) {
        super(config.getTitle());
        this.config = config;
        this.parent = parent;
    }

    @Override
    protected void init() {
        tabs = this.config.getTabs().stream()
                .map(configTab -> new ConfigScreenTab(configTab, client))
                .toArray(ConfigScreenTab[]::new);
        this.tabNavigation = TabNavigationWidget.builder(this.tabManager, this.width)
                .tabs(tabs)
                .build();
        
        this.tabNavigation.selectTab(0, false);

        DirectionalLayoutWidget directionalLayoutWidget = this.threePartsLayoutWidget.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("reconfigure.cancel"), button -> close()).build());
        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("reconfigure.save"), button -> saveAndClose()).build());

        this.threePartsLayoutWidget.forEachChild(this::addDrawableChild);

        this.addDrawableChild(this.tabNavigation);
        refreshWidgetPositions();
    }

    private void saveAndClose() {
        for (ConfigScreenTab tab : this.tabs) {
            tab.onSave();
        }
        this.config.save();
        this.close();
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
