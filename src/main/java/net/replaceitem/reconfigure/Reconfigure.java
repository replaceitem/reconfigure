package net.replaceitem.reconfigure;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class Reconfigure implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String NAMESPACE = "reconfigure";
    
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(NAMESPACE, path);
    }
    
    @Override
    public void onInitializeClient() {
    }
}
