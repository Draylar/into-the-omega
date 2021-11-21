package draylar.intotheomega.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class Stances {

    public static final Stance NONE = (player, model) -> {
        model.torso.yaw = 0;
        model.torso.pitch = 0;
        model.torso.roll = 0;
    };

    public static final Stance HANDS_UP = (player, model) -> {
//        model.rightArm.pitch = 100;
        // 4169
        model.rightArm.yaw = 3.14f;
        model.rightArm.roll = -.5f;
        model.rightArm.pitch = 3f;
        model.torso.yaw = .25f;
    };

    public static final Stance GLIDING = (player, model) -> {
        model.rightArm.yaw = 3.14f;
        model.rightArm.roll = -0.3f;
        model.rightArm.pitch = 3f;

        model.leftArm.yaw = 3.14f;
        model.leftArm.roll = .3f;
        model.leftArm.pitch = 3f;

        // legs
        model.leftLeg.pitch = 0.0f;
        model.rightLeg.pitch = 0.0f;
    };

    public static final Stance SWORD_DOWN = (player, model) -> {
//        model.rightArm.pitch = 100;
        // 4169
        model.rightArm.yaw = 2.9f;
        model.rightArm.roll = 0f;
        model.rightArm.pitch = 1.5f;
        model.leftArm.yaw = 3.8f;
        model.leftArm.pitch = 1.5f;
        model.torso.yaw = .25f;
    };

    public static void set(PlayerEntity player, Stance stance) {
        ((StanceAccessor) player).setStance(stance);
    }

    public static void reset(PlayerEntity player) {
        set(player, NONE);
    }

    public static Stance get(PlayerEntity entity) {
        return ((StanceAccessor) entity).getStance();
    }
}
