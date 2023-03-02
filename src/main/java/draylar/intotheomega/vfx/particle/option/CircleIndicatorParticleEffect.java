package draylar.intotheomega.vfx.particle.option;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public record CircleIndicatorParticleEffect(double radius, int color, int duration) implements ParticleEffect {

    private static final CircleIndicatorParticleEffect DEFAULT_OPTIONS = new CircleIndicatorParticleEffect(1.0, 0xffffffff, 20);

    public static final Codec<CircleIndicatorParticleEffect> CODEC = Codec.unit(DEFAULT_OPTIONS);
    public static final Factory<CircleIndicatorParticleEffect> SERIALIZER = new ParticleEffect.Factory<>() {

        @Override
        public CircleIndicatorParticleEffect read(ParticleType<CircleIndicatorParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double radius = reader.readDouble();
            reader.expect(' ');
            int r = reader.readInt();
            reader.expect(' ');
            int g = reader.readInt();
            reader.expect(' ');
            int b = reader.readInt();
            reader.expect(' ');
            int duration = reader.readInt();
            return new CircleIndicatorParticleEffect(radius, MathHelper.packRgb(r, g, b), duration);
        }

        @Override
        public CircleIndicatorParticleEffect read(ParticleType<CircleIndicatorParticleEffect> type, PacketByteBuf buffer) {
            return new CircleIndicatorParticleEffect(
                    buffer.readDouble(),
                    buffer.readInt(),
                    buffer.readInt()
            );
        }
    };

    @Override
    public ParticleType<?> getType() {
        return OmegaParticles.CIRCLE_INDICATOR;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(radius);
        buf.writeInt(color);
        buf.writeInt(duration);
    }

    @Override
    public String asString() {
        return Registry.PARTICLE_TYPE.getId(this.getType()).toString();
    }
}
