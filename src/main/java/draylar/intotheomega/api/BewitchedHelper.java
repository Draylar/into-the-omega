package draylar.intotheomega.api;

import draylar.intotheomega.impl.Bewitchable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;

public class BewitchedHelper {

    public static void bewitch(EndermanEntity enderman, PlayerEntity source) {
        ((Bewitchable) enderman).setBewitched(source);
    }

    public static boolean isBewitchedTo(EndermanEntity enderman, PlayerEntity source) {
        return ((Bewitchable) enderman).isBewitchedTo(source);
    }

    public static boolean isBewitched(EndermanEntity enderman) {
        return ((Bewitchable) enderman).isBewitched();
    }
}
