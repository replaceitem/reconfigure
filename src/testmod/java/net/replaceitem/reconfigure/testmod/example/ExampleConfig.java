package net.replaceitem.reconfigure.testmod.example;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.replaceitem.reconfigure.config.Config;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.config.ConfigTab;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

public class ExampleConfig {
    
    public final Config CONFIG = Config.builder("reconfigure-test").build();
    
    public final ConfigTab MAIN_TAB = CONFIG.createTab("main").build();
    
    Void HEADLINE_BASIC = MAIN_TAB.createHeadline("basic_headline");
    public final Property<String> TITLE = MAIN_TAB.createStringProperty("title").asTextField().build();
    public final Property<String> OTHER_NAME = MAIN_TAB.createStringProperty("othername").asTextField().displayName(Text.of("Alt display name")).build();
    public final Property<Boolean> ENABLED = MAIN_TAB.createBooleanProperty("enabled").asCheckbox().build();
    public final Property<Boolean> AUTOJUMP = MAIN_TAB.createBooleanProperty("autojump").asToggleButton().build();

    Void HEADLINE_SLIDERS = MAIN_TAB.createHeadline(Text.literal("Sliders"));
    public final Property<Integer> INT = MAIN_TAB.createIntegerProperty("size").range(1,10).asSlider().displayName(Text.literal("integer 1-10")).build();
    public final Property<Integer> INT_STEP = MAIN_TAB.createIntegerProperty("size").range(1, 12).asSlider().displayName(Text.literal("integer 1-12 step 3")).step(3).build();
    public final Property<Double> DOUBLE = MAIN_TAB.createDoubleProperty("size").range(-0.5, 15.0).asSlider().displayName(Text.literal("dbl -.5-15")).build();
    public final Property<Double> DOUBle_STEP = MAIN_TAB.createDoubleProperty("size").range(-0.5, 15.2).asSlider().displayName(Text.literal("dbl -.5-15.2 step 0.25")).step(0.25).build();
    public final Property<Double> BIG_DOUBLE = MAIN_TAB.createDoubleProperty("size").range(0.0, 15000.0).asSlider().displayName(Text.literal("big dbl 0-15000")).build();
    public final Property<Double> SMALL_DOUBLE = MAIN_TAB.createDoubleProperty("size").range(0.0, 0.001).asSlider().displayName(Text.literal("small dbl 0-0.001")).build();

    Void HEADLINE_ENUMS = MAIN_TAB.createHeadline(Text.literal("Enums"));
    public final Property<DayOfWeek> DAY_OF_WEEK = MAIN_TAB
            .createEnumProperty("day_of_week", DayOfWeek.class)
            .defaultValue(DayOfWeek.SUNDAY)
            .asCyclingButton()
            .displayName(Text.literal("DayOfWeek"))
            .build();
    public final Property<String> STRING_ENUM = MAIN_TAB
            .createEnumProperty("string_enum", List.of("Vanilla", "Chocolate", "Strawberry", "Nutella"))
            .asCyclingButton()
            .valueToText(s -> Text.literal(s).styled(style -> style.withColor(Formatting.AQUA)))
            .displayName(Text.literal("Ice cream type"))
            .build();
    public final Property<Month> LIMITED_OPTIONS = MAIN_TAB
            .createEnumProperty("string_enum", Month.class)
            .asCyclingButton()
            .valueToText(s -> Text.literal(s.toString()))
            .displayName(Text.literal("Month"))
            .values(List.of(Month.JANUARY, Month.JUNE, Month.DECEMBER))
            .build();

    Void TEXT = MAIN_TAB.createHeadline(Text.literal("Text"));
    public final Property<String> TEXT_BOX = MAIN_TAB.createStringProperty("box").asEditBox().build();
}
