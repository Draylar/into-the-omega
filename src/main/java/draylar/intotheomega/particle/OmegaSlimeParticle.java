package draylar.intotheomega.particle;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

public class OmegaSlimeParticle implements ParticleFactory<DefaultParticleType> {

    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double d, double e, double f, double g, double h, double i) {
        CrackParticle particle = new CrackParticle(world, d, e, f, new ItemStack(OmegaItems.OMEGA_SLIME));
        Vec3d velocity = new Vec3d(world.random.nextDouble() * 2 - 1, -1, world.random.nextDouble() * 2 - 1);
        velocity = velocity.add(g, h, i);
        particle.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
        return particle;
    }
}
