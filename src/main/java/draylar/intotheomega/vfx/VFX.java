package draylar.intotheomega.vfx;

import draylar.intotheomega.util.ParticleUtils;
import draylar.intotheomega.vfx.particle.option.CircleIndicatorParticleEffect;
import draylar.intotheomega.vfx.particle.option.LengthExpandParticleEffect;
import net.minecraft.world.World;

public class VFX {

    public static void circleIndicator(World world, double x, double y, double z, double radius, int color, int durationTicks) {
        ParticleUtils.spawnParticles(
                world,
                new CircleIndicatorParticleEffect(radius, color, durationTicks),
                true,
                x,
                y,
                z,
                1,
                0,
                0,
                0,
                0
        );
    }

    public static void lengthExpandIndicator(World world, double x, double y, double z, double width, double depth, double angle, int color, int durationTicks) {
        ParticleUtils.spawnParticles(
                world,
                new LengthExpandParticleEffect(width, depth, angle, color, durationTicks),
                true,
                x,
                y,
                z,
                1,
                0,
                0,
                0,
                0
        );
    }
}
