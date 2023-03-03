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

public record LengthExpandParticleEffect(double width, double depth, double angle, int color, int duration) implements ParticleEffect {

    private static final LengthExpandParticleEffect DEFAULT_OPTIONS = new LengthExpandParticleEffect(1.0, 5.0, 0, 0xffffffff, 20);

    public static final Codec<LengthExpandParticleEffect> CODEC = Codec.unit(DEFAULT_OPTIONS);
    public static final Factory<LengthExpandParticleEffect> SERIALIZER = new Factory<>() {

        @Override
        public LengthExpandParticleEffect read(ParticleType<LengthExpandParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double width = reader.readDouble();
            reader.expect(' ');
            double depth = reader.readDouble();
            reader.expect(' ');
            double angle = reader.readDouble();
            reader.expect(' ');
            int r = reader.readInt();
            reader.expect(' ');
            int g = reader.readInt();
            reader.expect(' ');
            int b = reader.readInt();
            reader.expect(' ');
            int duration = reader.readInt();
            return new LengthExpandParticleEffect(width, depth, angle, MathHelper.packRgb(r, g, b), duration);
        }

        @Override
        public LengthExpandParticleEffect read(ParticleType<LengthExpandParticleEffect> type, PacketByteBuf buffer) {
            return new LengthExpandParticleEffect(
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readInt(),
                    buffer.readInt()
            );
        }
    };

    @Override
    public ParticleType<?> getType() {
        return OmegaParticles.LENGTH_EXPAND_INDICATOR;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(width);
        buf.writeDouble(depth);
        buf.writeDouble(angle);
        buf.writeInt(color);
        buf.writeInt(duration);
    }

    @Override
    public String asString() {
        return Registry.PARTICLE_TYPE.getId(this.getType()).toString();
    }
}
