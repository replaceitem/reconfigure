package net.replaceitem.reconfigure.testmod.example;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.replaceitem.reconfigure.api.*;
import net.replaceitem.reconfigure.api.serializer.Serializers;

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
    public final Property<String> TITLE = SIMPLE_TAB.createStringProperty("title").addValidator(Validator.ofPredicate(s -> s.length() <= 4, Text.translatable("reconfigure-testmod.validation.max_length", 4))).asTextField().build();
    public final Property<String> OTHER_NAME = SIMPLE_TAB.createStringProperty("othername").asTextField().displayName(Text.of("Alt display name")).placeholder().build();
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
    public final Property<String> TEXT_BOX = SIMPLE_TAB.createStringProperty("box").asEditBox().placeholder(Text.literal("Enter some text")).build();
    
    Void LIST = SIMPLE_TAB.createHeadline(Text.literal("List"));
    public final Property<List<String>> CHIP_LIST = SIMPLE_TAB.createListProperty("chiplist").asChipList().build();





    class DemoTab {
        public final ConfigTab DEMO_TAB = CONFIG.createTab("demo").build();
        Void HEADLINE = DEMO_TAB.createHeadline("demo_headline");
        public final Property<String> STRING = DEMO_TAB.createStringProperty("demo_string").defaultValue("Hello world").asTextField().displayName(Text.literal("Text field")).build();
        public final Property<String> STRING_PLACEHOLDER = DEMO_TAB.createStringProperty("demo_string_placeholder").asTextField().displayName(Text.literal("Text field with placeholder")).placeholder(Text.literal("Enter something")).build();
        public final Property<Boolean> CHECKBOX = DEMO_TAB.createBooleanProperty("demo_checkbox").defaultValue(true).asCheckbox().displayName(Text.literal("Checkbox")).build();
        public final Property<Boolean> TOGGLE_BUTTON = DEMO_TAB.createBooleanProperty("demo_toggle_button").asToggleButton().displayName(Text.literal("Toggle button")).build();
        public final Property<Integer> SLIDER = DEMO_TAB.createIntegerProperty("demo_slider").defaultValue(4).range(1,10).asSlider().displayName(Text.literal("Slider")).build();

        public final Property<DayOfWeek> CYCLE_BUTTON = DEMO_TAB
                .createEnumProperty("demo_cycle_button", DayOfWeek.class)
                .defaultValue(DayOfWeek.FRIDAY)
                .asCyclingButton()
                .displayName(Text.literal("Enum cycling button"))
                .build();
        
        public final Property<Integer> COLOR = DEMO_TAB.createIntegerProperty("demo_color").defaultValue(0xff136bc3).asColorPicker().displayName(Text.literal("Color")).build();

        public final Property<String> EDIT_BOX = DEMO_TAB.createStringProperty("demo_box").defaultValue("""
            This is a multiline text box.
            It supports scrolling.
            The it fits a lot of text.
            You could write a lot here.
            Like a whole book.
            But that's not the point of a configuration library.
            Oh look now it's clipping because of scrolling.
            This text won't be visible anymore.
            But you can edit all of this.""").asEditBox().displayName(Text.literal("Multiline Edit Box")).build();

        public final Property<List<String>> CHIP_LIST = DEMO_TAB.createListProperty("demo_chiplist")
                .defaultValue(List.of("Apple", "Banana", "Orange", "Strawberry", "Cherry", "Raspberry", "Kiwi", "Pineapple"))
                .asChipList()
                .displayName(Text.literal("Chip list"))
                .build();

    }
    
    DemoTab demoTab = new DemoTab();
    
    
    
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
