package draylar.intotheomega.impl.event.client;

import draylar.intotheomega.api.Color;
import draylar.intotheomega.api.event.ParticleEvents;
import draylar.intotheomega.api.particle.DirectParticle;
import draylar.intotheomega.client.particle.*;
import draylar.intotheomega.entity.void_matrix.beam.particle.VoidBeamDustParticle;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

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
        manager.registerFactory(OmegaParticles.STARFALL_NODE, StarfallNodeParticle.Factory::new);
        manager.registerFactory(OmegaParticles.STARFALL_SWIRL, StarfallSwirlParticle.Factory::new);
        manager.registerFactory(OmegaParticles.TINY_STAR, TinyStarParticle.Factory::new);
        manager.registerFactory(OmegaParticles.STARLIGHT, StarlightParticle.Factory::new);
        manager.registerFactory(OmegaParticles.ORIGIN_NOVA, OriginNovaParticle.Factory::new);
        manager.registerFactory(OmegaParticles.NOVA_STRIKE, NovaStrikeParticle.Factory::new);
        manager.registerFactory(OmegaParticles.QUASAR_SLASH, QuasarSlashParticle.Factory::new);

        register(manager, OmegaParticles.VOID_BEAM_DUST, VoidBeamDustParticle::new);
        register(manager, OmegaParticles.CIRCLE_INDICATOR, CircleIndicatorParticle::new);
        register(manager, OmegaParticles.LENGTH_EXPAND_INDICATOR, LengthExpandIndicatorParticle::new);
        register(manager, OmegaParticles.MATRIX_STAR, MatrixStarParticle::new);
        register(manager, OmegaParticles.MATRIX_BLAST_WALL, MatrixBlastWallParticle::new);
    }

    private static <T extends ParticleEffect> void register(ParticleManager manager, ParticleType<T> type, Factory<T> factory) {
        manager.registerFactory(type, sprite -> {
            return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                return factory.create(sprite, parameters, world, x, y, z, velocityX, velocityY, velocityZ);
            };
        });
    }

    /**
     * Particle factory with arguments matching {@link DirectParticle} primary constructor for method referencing.
     *
     * @param <T>
     */
    private interface Factory<T extends ParticleEffect> {

        DirectParticle create(SpriteProvider spriteProvider, T parameters, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ);
    }
}
