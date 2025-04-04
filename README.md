# reconfigure

A config library for my mods

[<img alt="Documentation" height="56" src="https://badges.penpow.dev/badges/documentation/generic/cozy.svg">](https://replaceitem.github.io/reconfigure/)
[<img alt="Available for fabric" height="56" src="https://badges.penpow.dev/badges/supported/fabric/cozy.svg">](https://fabricmc.net/)
[<img alt="Requires fabric api" height="56" src="https://badges.penpow.dev/badges/requires/fabric-api/cozy.svg">](https://modrinth.com/mod/fabric-api)
[<img alt="Available on Modrinth" height="56" src="https://badges.penpow.dev/badges/available/modrinth/cozy.svg">](https://modrinth.com/mod/reconfigure)
[<img alt="See me on GitHub" height="56" src="https://badges.penpow.dev/badges/social/github-singular/cozy.svg">](https://github.com/replaceitem)
[<img alt="Chat on Discord" height="56" src="https://badges.penpow.dev/badges/social/discord-singular/cozy.svg">](https://discord.gg/etTDQAVSgt)

## Design goals

* Uses the builder pattern so all options autocomplete. Just start with `Config.builder()` and autocompletion will guide you through the whole process of creating a configuration
* Designed to be used in the field initializers of a config class
* Does not use annotations for flexibility at runtime

## Preview

![Basic widgets](https://github.com/replaceitem/reconfigure/blob/main/widgets-1.png?raw=true)
![Advanced widgets](https://github.com/replaceitem/reconfigure/blob/main/widgets-2.png?raw=true)

## Example definition

```java
class DemoConfig {
    public final Config CONFIG = Config.builder("reconfigure-test")
            .serializer(Serializers.JSON)
            .build();
    public final ConfigTab DEMO_TAB = CONFIG.createTab("demo").build();
    Void HEADLINE = DEMO_TAB.createHeadline("headline");
    public final Property<String> STRING = DEMO_TAB.createStringProperty("string").defaultValue("Hello world")
            .asTextField().placeholder("Enter something").build();
    public final Property<Boolean> CHECKBOX = DEMO_TAB.createBooleanProperty("checkbox").defaultValue(true)
            .asCheckbox().build();
    public final Property<Boolean> TOGGLE_BUTTON = DEMO_TAB.createBooleanProperty("toggle_button")
            .asToggleButton().build();
    public final Property<Integer> SLIDER = DEMO_TAB.createIntegerProperty("slider").defaultValue(4).range(1,10)
            .asSlider().build();

    public final Property<DayOfWeek> CYCLE_BUTTON = DEMO_TAB
            .createEnumProperty("cycle_button", DayOfWeek.class)
            .defaultValue(DayOfWeek.MONDAY)
            .asCyclingButton()
            .build();

    public final Property<Integer> COLOR = DEMO_TAB.createIntegerProperty("color").asColorPicker().build();
    public final Property<String> EDIT_BOX = DEMO_TAB.createStringProperty("box").asEditBox().build();
    public final Property<List<String>> CHIP_LIST = DEMO_TAB.createListProperty("chiplist")
            .asChipList()
            .build();
}
```