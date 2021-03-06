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

    public static void init() {

    }

    private OmegaParticles() {
        // NO-OP
    }
}
