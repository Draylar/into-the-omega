package draylar.intotheomega.gui;

import draylar.intotheomega.library.gui.*;
import draylar.intotheomega.library.gui.layout.PaneLayout;
import draylar.intotheomega.library.gui.widget.Button;
import net.minecraft.text.LiteralText;

import static draylar.intotheomega.library.gui.Size.percentageHeight;
import static draylar.intotheomega.library.gui.Size.percentageWidth;

public class DemoPaneMenu extends Menu {

    @Override
    public MenuNode<?> compose() {
        return PaneLayout.create(
                Modifier.create(modifier -> {
                    modifier.x(percentageWidth(0.25));
                    modifier.y(percentageHeight(0.25));
                    modifier.height(percentageHeight(0.5));
                    modifier.width(percentageWidth(0.5));
                    modifier.background(BackgroundRenderer.themed(MenuTheme.BEDROCK));
                }),
                Button.create(
                        Modifier.create()
                                .x(percentageWidth(0.10))
                                .y(percentageHeight(1.0 - 0.25 - 0.2))
                                .height(percentageHeight(0.25))
                                .width(percentageWidth(0.30))
                                .background(BackgroundRenderer.themed(MenuTheme.BEDROCK)),
                        new LiteralText("I agree!")
                ),
                Button.create(
                        Modifier.create()
                                .x(percentageWidth(0.60))
                                .y(percentageHeight(1.0 - 0.25 - 0.2))
                                .height(percentageHeight(0.25))
                                .width(percentageWidth(0.30))
                                .background(BackgroundRenderer.themed(MenuTheme.BEDROCK)),
                        new LiteralText("No thanks!")
                )
        );
    }
}
