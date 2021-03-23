package draylar.intotheomega.registry;

import draylar.intotheomega.mixin.ParticleTypesAccessor;
import net.minecraft.particle.DefaultParticleType;

public class OmegaParticles {

    public static final DefaultParticleType OMEGA_PARTICLE = ParticleTypesAccessor.callRegister("intotheomega:default", false);
    public static final DefaultParticleType DARK = ParticleTypesAccessor.callRegister("intotheomega:dark", false);
    public static final DefaultParticleType OMEGA_SLIME = ParticleTypesAccessor.callRegister("intotheomega:omega_slime", false);
    public static final DefaultParticleType SMALL_OMEGA_BURST = ParticleTypesAccessor.callRegister("intotheomega:small_omega_burst", false);
    public static final DefaultParticleType VARIANT_FUSION = ParticleTypesAccessor.callRegister("intotheomega:variant_fusion", false);

    public static void init() {

    }

    private OmegaParticles() {
        // NO-OP
    }
}
