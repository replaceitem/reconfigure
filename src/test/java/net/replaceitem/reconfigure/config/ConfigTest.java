package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.Config;
import net.replaceitem.reconfigure.api.ConfigTab;
import net.replaceitem.reconfigure.api.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    private static class MockConfig extends ConfigImpl {
        boolean hasSaved = false;

        protected MockConfig(String namespace, Text title) {
            super(namespace, title, null);
        }

        @Override
        public void save() {
            hasSaved = true;
        }
    }

    @Test
    void testIsDirty() {
        Config config = Config.builder("test").build();
        ConfigTab tab = config.createDefaultTab().build();
        Property<String> test = tab.createStringProperty("test").defaultValue("ABC").buildWithoutWidget();
        assertFalse(config.isDirty());
        test.set("ABC");
        assertFalse(config.isDirty());
        test.set("XYZ");
        assertTrue(config.isDirty());
        test.set("ABC");
        assertTrue(config.isDirty());
    }

    @Test
    void testSaveIfDirty() {
        MockConfig config = new MockConfig("test", Text.empty());
        ConfigTab tab = config.createDefaultTab().build();
        Property<String> test = tab.createStringProperty("test").defaultValue("ABC").buildWithoutWidget();
        config.saveIfDirty();
        assertFalse(config.hasSaved);
        test.set("XYZ");
        config.saveIfDirty();
        assertTrue(config.hasSaved);
    }
}