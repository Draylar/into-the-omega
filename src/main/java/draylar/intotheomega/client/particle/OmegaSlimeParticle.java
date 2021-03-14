package draylar.intotheomega.client.particle;

import draylar.intotheomega.mixin.CrackParticleAccessor;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;

public class OmegaSlimeParticle implements ParticleFactory<DefaultParticleType> {

    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        return CrackParticleAccessor.createCrackParticle(clientWorld, d, e, f, new ItemStack(OmegaItems.OMEGA_SLIME));
    }
}
