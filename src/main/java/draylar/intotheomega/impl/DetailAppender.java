package draylar.intotheomega.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class DetailAppender {

    @Environment(EnvType.CLIENT)
    public static void append(MutableText append, int level, String translationKey, int maxLevel) {
        if(Screen.hasControlDown()) {
            append.append(new LiteralText(" (").append(new TranslatableText(translationKey)).append(" ").append(String.valueOf(level + maxLevel)).append(")"));
        }
    }
}
