package draylar.intotheomega.gui;

import draylar.intotheomega.library.gui.*;
import draylar.intotheomega.library.gui.layout.PaneLayout;
import draylar.intotheomega.library.gui.state.MutableState;
import draylar.intotheomega.library.gui.widget.Button;
import draylar.intotheomega.library.gui.widget.Dropdown;
import draylar.intotheomega.library.gui.widget.Label;
import draylar.intotheomega.library.gui.widget.LabeledDropdown;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.util.List;

import static draylar.intotheomega.library.gui.Size.*;

public class ParticleMenu extends Menu {

    private final MutableState<Integer> loopType = state(Integer.class, 0);

    @Override
    public MenuElement<?> compose() {
        return PaneLayout.create(
                Modifier.create()
                        .width(remainingWidth())
                        .height(percentageHeight(1.0))
                        .x(percentageWidth(0.8))
                        .y(fixed(0))
                        .background(BackgroundRenderer.themed(MenuThemes.MODERN)),

                Label.create(
                        Modifier.create()
                                .y(percentageHeight(0.025))
                                .x(percentageWidth(0.0))
                                .width(percentageWidth(1.0))
                                .height(percentageHeight(0.05))
                                .background(BackgroundRenderer.themed(MenuThemes.MODERN)),
                        new LiteralText("Particle Editor").formatted(Formatting.WHITE)),

                Button.create(
                        Modifier.create()
                                .y(percentageHeight(0.10))
                                .x(percentageWidth(0.0))
                                .width(percentageWidth(1.0))
                                .height(percentageHeight(0.05))
                                .background(BackgroundRenderer.themed(MenuThemes.MODERN)),
                        new LiteralText("Particle Meta").formatted(Formatting.GREEN), () -> {}),

                new LabeledDropdown(Modifier.create()
                        .y(percentageHeight(0.15))
                        .x(percentageWidth(0.0))
                        .width(percentageWidth(1.0))
                        .height(percentageHeight(0.05))
                        .background(BackgroundRenderer.themed(MenuThemes.MODERN))),

                new LabeledDropdown(Modifier.create()
                        .y(percentageHeight(0.175))
                        .x(percentageWidth(0.0))
                        .width(percentageWidth(1.0))
                        .height(percentageHeight(0.05))
                        .background(BackgroundRenderer.themed(MenuThemes.MODERN))),

                Button.create(
                        Modifier.create()
                                .y(percentageHeight(0.92))
                                .x(percentageWidth(0.1))
                                .width(percentageWidth(0.4))
                                .height(percentageHeight(0.05))
                                .background(BackgroundRenderer.themed(MenuThemes.MODERN)),
                        new LiteralText("Spawn"), () -> {}),

                Dropdown.create(
                        Modifier.create()
                                .y(percentageHeight(0.92))
                                .x(percentageWidth(0.5))
                                .width(percentageWidth(0.4))
                                .height(percentageHeight(0.05))
                                .background(BackgroundRenderer.themed(MenuThemes.MODERN)),
                        Dropdown.Type.UPWARDS,
                        List.of("Once", "Looping"),
                        loopType
                )
        );
    }
}
