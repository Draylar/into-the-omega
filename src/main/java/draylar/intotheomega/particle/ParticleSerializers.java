package draylar.intotheomega.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

import java.util.function.Function;

public class ParticleSerializers {

    public static <T extends ParticleEffect> ParticleEffect.Factory<T> serializer(T defaultValue, Function<PacketByteBuf, T> factory) {
        return new ParticleEffect.Factory<T>() {

            @Override
            public T read(ParticleType<T> type, StringReader reader) throws CommandSyntaxException {
                return defaultValue;
            }

            @Override
            public T read(ParticleType<T> type, PacketByteBuf buf) {
                return factory.apply(buf);
            }
        };
    }
}
