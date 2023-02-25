package draylar.intotheomega.mixin.access;

import com.mojang.serialization.Codec;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;

@Mixin(ParticleTypes.class)
public interface ParticleTypesAccessor {

    @Invoker
    static DefaultParticleType callRegister(String name, boolean alwaysShow) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static <T extends ParticleEffect> ParticleType<T> callRegister(String name, ParticleEffect.Factory<T> factory, final Function<ParticleType<T>, Codec<T>> function) {
        throw new UnsupportedOperationException();
    }
}
