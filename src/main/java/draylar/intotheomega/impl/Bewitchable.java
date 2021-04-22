package draylar.intotheomega.impl;

import net.minecraft.entity.player.PlayerEntity;

public interface Bewitchable {
    boolean isBewitchedTo(PlayerEntity player);
    boolean isBewitched();
    void setBewitched(PlayerEntity source);
}
