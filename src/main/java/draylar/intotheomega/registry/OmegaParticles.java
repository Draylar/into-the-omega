package draylar.intotheomega.registry;

import draylar.intotheomega.mixin.ParticleTypesAccessor;
import net.minecraft.particle.DefaultParticleType;

public class OmegaParticles {

    public static final DefaultParticleType OMEGA_PARTICLE = ParticleTypesAccessor.callRegister("intotheomega:default", false);

    public static void init() {

    }

    private OmegaParticles() {
        // NO-OP
    }
}
