package net.replaceitem.reconfigure.testmod;

import net.fabricmc.api.ClientModInitializer;
import net.replaceitem.reconfigure.testmod.example.ExampleConfig;

public class Testmod implements ClientModInitializer {
    public static final ExampleConfig CONFIG = new ExampleConfig();
    
    @Override
    public void onInitializeClient() {
        CONFIG.CONFIG.load();
    }
}
