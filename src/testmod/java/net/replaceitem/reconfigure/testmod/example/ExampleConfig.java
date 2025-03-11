package net.replaceitem.reconfigure.testmod.example;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.replaceitem.reconfigure.api.*;
import net.replaceitem.reconfigure.config.property.PropertyImpl;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class ExampleConfig {
    
    public final Config CONFIG = Config.builder("reconfigure-test")
            .serializer(Serializers.JSON)
            .build();
    
    public final ConfigTab SIMPLE_TAB = CONFIG.createTab("simple").build();
    
    Void HEADLINE_STRINGS = SIMPLE_TAB.createHeadline("strings");
    public final Property<String> TITLE = SIMPLE_TAB.createStringProperty("title").addValidator(Validator.ofPredicate(s -> s.length() < 5, "Must be at most 4 characters")).asTextField().build();
    public final Property<String> OTHER_NAME = SIMPLE_TAB.createStringProperty("othername").asTextField().displayName(Text.of("Alt display name")).placeholder("Enter something").build();
    public final Property<String> REGEX = SIMPLE_TAB.createStringProperty("regex").asTextField().displayName(Text.of("Regular Expression")).build();
    public final Bindable<Pattern> COMPILED_REGEX = REGEX.map(Pattern::compile);
    
    Void HEADLINE_BOOLEANS = SIMPLE_TAB.createHeadline("booleans");
    public final Property<Boolean> ENABLED = SIMPLE_TAB.createBooleanProperty("enabled").asCheckbox().build();
    public final Property<Boolean> AUTOJUMP = SIMPLE_TAB.createBooleanProperty("autojump").asToggleButton().build();

    Void HEADLINE_SLIDERS = SIMPLE_TAB.createHeadline(Text.literal("Sliders"));
    public final Property<Integer> INT = SIMPLE_TAB.createIntegerProperty("size_int").range(1,10).asSlider().displayName(Text.literal("integer 1-10")).build();
    public final Property<Integer> INT_STEP = SIMPLE_TAB.createIntegerProperty("size_int_step").range(1, 12).asSlider().displayName(Text.literal("integer 1-12 step 3")).step(3).build();
    public final Property<Double> DOUBLE = SIMPLE_TAB.createDoubleProperty("size_double").range(-0.5, 15.0).asSlider().displayName(Text.literal("dbl -.5-15")).build();
    public final Property<Double> DOUBle_STEP = SIMPLE_TAB.createDoubleProperty("size_double_step").range(-0.5, 15.2).asSlider().displayName(Text.literal("dbl -.5-15.2 step 0.25")).step(0.25).build();
    public final Property<Double> BIG_DOUBLE = SIMPLE_TAB.createDoubleProperty("size_big_double").range(0.0, 15000.0).asSlider().displayName(Text.literal("big dbl 0-15000")).build();
    public final Property<Double> SMALL_DOUBLE = SIMPLE_TAB.createDoubleProperty("size_small_double").range(0.0, 0.001).asSlider().displayName(Text.literal("small dbl 0-0.001")).build();

    Void HEADLINE_COLORS = SIMPLE_TAB.createHeadline(Text.literal("Colors"));
    public final Property<Integer> COLOR = SIMPLE_TAB.createIntegerProperty("color").asColorPicker().displayName(Text.literal("Color")).build();
    
    
    Void HEADLINE_ENUMS = SIMPLE_TAB.createHeadline(Text.literal("Enums"));
    public final Property<DayOfWeek> DAY_OF_WEEK = SIMPLE_TAB
            .createEnumProperty("day_of_week", DayOfWeek.class)
            .defaultValue(DayOfWeek.SUNDAY)
            .asCyclingButton()
            .displayName(Text.literal("DayOfWeek"))
            .build();
    public final Property<String> STRING_ENUM = SIMPLE_TAB
            .createEnumProperty("string_enum", List.of("Vanilla", "Chocolate", "Strawberry", "Nutella"))
            .asCyclingButton()
            .valueToText(s -> Text.literal(s).styled(style -> style.withColor(Formatting.AQUA)))
            .displayName(Text.literal("Ice cream type"))
            .tooltip(Text.of("Select the one you like"))
            .build();
    public final Property<Month> LIMITED_OPTIONS = SIMPLE_TAB
            .createEnumProperty("string_enum_limited", Month.class)
            .asCyclingButton()
            .valueToText(s -> Text.literal(s.toString()))
            .displayName(Text.literal("Month"))
            .values(List.of(Month.JANUARY, Month.JUNE, Month.DECEMBER))
            .build();
    public final Property<NumberValue> NON_ENUM = SIMPLE_TAB
            .createEnumProperty("non_enum", List.of(NumberValue.A, NumberValue.B))
            .asCyclingButton()
            .valueToText(s -> Text.literal(s.toString()))
            .displayName(Text.literal("Non enum"))
            .build();

    Void TEXT = SIMPLE_TAB.createHeadline(Text.literal("Text"));
    public final Property<String> TEXT_BOX = SIMPLE_TAB.createStringProperty("box").asEditBox().placeholder("Enter some text").build();
    
    Void LIST = SIMPLE_TAB.createHeadline(Text.literal("List"));
    public final PropertyImpl<List<String>> CHIP_LIST = SIMPLE_TAB.createListProperty("chiplist").asChipList().build();
    
    
    public static class NumberValue {
        public int num;
        public String name;

        public NumberValue(int num, String name) {
            this.num = num;
            this.name = name;
        }
        
        public static final NumberValue A = new NumberValue(2, "Two");
        public static final NumberValue B = new NumberValue(3, "Three");

        @Override
        public String toString() {
            return name;
        }
    }
}
