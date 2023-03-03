package draylar.intotheomega.registry;

import draylar.intotheomega.impl.event.client.OmegaParticleFactoryRegistrar;
import draylar.intotheomega.mixin.access.ParticleTypesAccessor;
import draylar.intotheomega.vfx.particle.option.CircleIndicatorParticleEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;

/**
 * @see OmegaParticleFactoryRegistrar
 */
public class OmegaParticles {

    public static final DefaultParticleType OMEGA_PARTICLE = ParticleTypesAccessor.register("intotheomega:default", false);
    public static final DefaultParticleType DARK = ParticleTypesAccessor.register("intotheomega:dark", false);
    public static final DefaultParticleType OMEGA_SLIME = ParticleTypesAccessor.register("intotheomega:omega_slime", false);
    public static final DefaultParticleType SMALL_OMEGA_BURST = ParticleTypesAccessor.register("intotheomega:small_omega_burst", false);
    public static final DefaultParticleType SMALL_BLUE_OMEGA_BURST = ParticleTypesAccessor.register("intotheomega:small_blue_omega_burst", false);
    public static final DefaultParticleType SMALL_PINK_OMEGA_BURST = ParticleTypesAccessor.register("intotheomega:small_pink_omega_burst", false);
    public static final DefaultParticleType VARIANT_FUSION = ParticleTypesAccessor.register("intotheomega:variant_fusion", false);
    public static final DefaultParticleType MATRIX_EXPLOSION = ParticleTypesAccessor.register("intotheomega:matrix_explosion", false);
    public static final DefaultParticleType ICE_FLAKE = ParticleTypesAccessor.register("intotheomega:ice_flake", false);
    public static final DefaultParticleType DARK_SAKURA_PETAL = ParticleTypesAccessor.register("intotheomega:dark_sakura_petal", false);
    public static final DefaultParticleType STARFALL_NODE = ParticleTypesAccessor.register("intotheomega:starfall_node", false);
    public static final DefaultParticleType STARFALL_SWIRL = ParticleTypesAccessor.register("intotheomega:starfall_swirl", false);
    public static final DefaultParticleType TINY_STAR = ParticleTypesAccessor.register("intotheomega:tiny_star", false);
    public static final DefaultParticleType STARLIGHT = ParticleTypesAccessor.register("intotheomega:starlight", false);
    public static final DefaultParticleType ORIGIN_NOVA = ParticleTypesAccessor.register("intotheomega:origin_nova", false);
    public static final DefaultParticleType NOVA_STRIKE = ParticleTypesAccessor.register("intotheomega:nova_strike", false);
    public static final DefaultParticleType QUASAR_SLASH = ParticleTypesAccessor.register("intotheomega:quasar_slash", false);
    public static final DefaultParticleType VOID_MATRIX$SLAM_LENGTH_EXPAND_INDICATOR = ParticleTypesAccessor.register("intotheomega:void_matrix/slam_length_expand_indicator", false);
    public static final DefaultParticleType VOID_BEAM_DUST = ParticleTypesAccessor.register("intotheomega:void_matrix/beam/dust", false);
    public static final DefaultParticleType MATRIX_STAR = ParticleTypesAccessor.register("intotheomega:void_matrix/matrix_star", false);
    public static final DefaultParticleType MATRIX_BLAST_WALL = ParticleTypesAccessor.register("intotheomega:void_matrix/matrix_blast_wall", false);

    // Options
    public static final ParticleType<CircleIndicatorParticleEffect> CIRCLE_INDICATOR = ParticleTypesAccessor.register("intotheomega:vfx/circle_indicator", CircleIndicatorParticleEffect.SERIALIZER, type -> CircleIndicatorParticleEffect.CODEC);


    public static void init() {

    }

    private OmegaParticles() {
        // NO-OP
    }
}
