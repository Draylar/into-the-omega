package draylar.intotheomega.registry;

import draylar.intotheomega.mixin.ParticleTypesAccessor;
import net.minecraft.particle.DefaultParticleType;

public class OmegaParticles {

    public static final DefaultParticleType OMEGA_PARTICLE = ParticleTypesAccessor.callRegister("intotheomega:default", false);
    public static final DefaultParticleType DARK = ParticleTypesAccessor.callRegister("intotheomega:dark", false);
    public static final DefaultParticleType OMEGA_SLIME = ParticleTypesAccessor.callRegister("intotheomega:omega_slime", false);
    public static final DefaultParticleType SMALL_OMEGA_BURST = ParticleTypesAccessor.callRegister("intotheomega:small_omega_burst", false);
    public static final DefaultParticleType SMALL_BLUE_OMEGA_BURST = ParticleTypesAccessor.callRegister("intotheomega:small_blue_omega_burst", false);
    public static final DefaultParticleType SMALL_PINK_OMEGA_BURST = ParticleTypesAccessor.callRegister("intotheomega:small_pink_omega_burst", false);
    public static final DefaultParticleType VARIANT_FUSION = ParticleTypesAccessor.callRegister("intotheomega:variant_fusion", false);
    public static final DefaultParticleType MATRIX_EXPLOSION = ParticleTypesAccessor.callRegister("intotheomega:matrix_explosion", false);
    public static final DefaultParticleType ICE_FLAKE = ParticleTypesAccessor.callRegister("intotheomega:ice_flake", false);
    public static final DefaultParticleType DARK_SAKURA_PETAL = ParticleTypesAccessor.callRegister("intotheomega:dark_sakura_petal", false);
    public static final DefaultParticleType STARFALL_NODE = ParticleTypesAccessor.callRegister("intotheomega:starfall_node", false);
    public static final DefaultParticleType STARFALL_SWIRL = ParticleTypesAccessor.callRegister("intotheomega:starfall_swirl", false);
    public static final DefaultParticleType TINY_STAR = ParticleTypesAccessor.callRegister("intotheomega:tiny_star", false);
    public static final DefaultParticleType STARLIGHT = ParticleTypesAccessor.callRegister("intotheomega:starlight", false);
    public static final DefaultParticleType ORIGIN_NOVA = ParticleTypesAccessor.callRegister("intotheomega:origin_nova", false);

    public static void init() {

    }

    private OmegaParticles() {
        // NO-OP
    }
}
