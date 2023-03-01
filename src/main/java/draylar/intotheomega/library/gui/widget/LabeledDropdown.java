package draylar.intotheomega.library.gui.widget;

import draylar.intotheomega.library.gui.*;
import draylar.intotheomega.library.gui.state.MutableState;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.util.List;

import static draylar.intotheomega.library.gui.Size.percentageHeight;
import static draylar.intotheomega.library.gui.Size.percentageWidth;

public class LabeledDropdown extends MenuComponent<LabeledDropdown> {

    public LabeledDropdown(Modifier modifier) {
        super(modifier);

        addChild(Label.create(
                Modifier.create()
                        .y(percentageHeight(0.0))
                        .x(percentageWidth(0.0))
                        .width(percentageWidth(0.3))
                        .height(percentageHeight(1.0))
                        .background(BackgroundRenderer.themed(MenuThemes.MODERN)),
                new LiteralText("Type").formatted(Formatting.WHITE)));

        addChild(Dropdown.create(
                Modifier.create()
                        .y(percentageHeight(0.1))
                        .x(percentageWidth(0.3))
                        .width(percentageWidth(0.6))
                        .height(percentageHeight(0.75))
                        .background(BackgroundRenderer.themed(MenuThemes.MODERN)),
                Dropdown.Type.DOWNWARDS, List.of("Particle", "Emitter", "Layered"), new MutableState<>(0)));
    }
}
