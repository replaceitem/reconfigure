package net.replaceitem.reconfigure;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;

public class Reconfigure implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String NAMESPACE = "reconfigure";
    
    @Override
    public void onInitializeClient() {
        
    }
}
