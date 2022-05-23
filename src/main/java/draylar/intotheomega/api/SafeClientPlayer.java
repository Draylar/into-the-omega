package draylar.intotheomega.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class SafeClientPlayer {

    public static PlayerEntity get() {
        return MinecraftClient.getInstance().player;
    }
}
