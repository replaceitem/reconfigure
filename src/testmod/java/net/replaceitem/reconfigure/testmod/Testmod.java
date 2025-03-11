package net.replaceitem.reconfigure.testmod;

import net.fabricmc.api.ClientModInitializer;
import net.replaceitem.reconfigure.testmod.example.ExampleConfig;

public class Testmod implements ClientModInitializer {
    public static final ExampleConfig CONFIG = new ExampleConfig();
    
    @Override
    public void onInitializeClient() {
        CONFIG.CONFIG.load();
        
        CONFIG.TITLE.observe(s -> System.out.println("Observing TITLE as " + s));
        CONFIG.COMPILED_REGEX.addListener(pattern -> System.out.println("Regex changed. Trying to match 'abc': " + pattern.matcher("abc").matches()));
    }
}
