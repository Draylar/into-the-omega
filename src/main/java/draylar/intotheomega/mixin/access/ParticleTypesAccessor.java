package draylar.intotheomega.mixin.access;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ParticleTypes.class)
public interface ParticleTypesAccessor {
    @Invoker
    static DefaultParticleType callRegister(String name, boolean alwaysShow) {
        throw new UnsupportedOperationException();
    }
}
