package net.replaceitem.reconfigure;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class Reconfigure implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String NAMESPACE = "reconfigure";
    
    public static Identifier id(String path) {
        return Identifier.of(NAMESPACE, path);
    }
    
    @Override
    public void onInitializeClient() {
        Shaders.init();
    }
}
