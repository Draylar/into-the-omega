package draylar.intotheomega.impl.event.client;

import draylar.intotheomega.api.Color;
import draylar.intotheomega.api.event.ParticleEvents;
import draylar.intotheomega.client.particle.*;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.particle.ParticleManager;

public class OmegaParticleFactoryRegistrar implements ParticleEvents.RegistryHandler {

    @Override
    public void onRegister(ParticleManager manager) {
        manager.registerFactory(OmegaParticles.OMEGA_PARTICLE, provider -> new OmegaParticle.OmegaParticleFactory(provider, new Color(0.9F, 0.1F, 0.9F)));
        manager.registerFactory(OmegaParticles.DARK, provider -> new OmegaParticle.OmegaParticleFactory(provider, new Color(0.34F, 0.03F, 0.47F)));
        manager.registerFactory(OmegaParticles.OMEGA_SLIME, provider -> new OmegaSlimeParticle());
        manager.registerFactory(OmegaParticles.SMALL_OMEGA_BURST, SmallOmegaBurstParticle.Factory::new);
        manager.registerFactory(OmegaParticles.SMALL_BLUE_OMEGA_BURST, SmallOmegaBurstParticle.Factory::new);
        manager.registerFactory(OmegaParticles.SMALL_PINK_OMEGA_BURST, SmallOmegaBurstParticle.Factory::new);
        manager.registerFactory(OmegaParticles.VARIANT_FUSION, VariantFusionParticle.Factory::new);
        manager.registerFactory(OmegaParticles.MATRIX_EXPLOSION, MatrixExplosionParticle.Factory::new);
        manager.registerFactory(OmegaParticles.ICE_FLAKE, IceFlakeParticle.Factory::new);
        manager.registerFactory(OmegaParticles.DARK_SAKURA_PETAL, DarkSakuraPetalParticle.Factory::new);
    }
}
