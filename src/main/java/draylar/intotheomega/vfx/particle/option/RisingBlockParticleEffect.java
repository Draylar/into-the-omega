package draylar.intotheomega.vfx.particle.option;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public record RisingBlockParticleEffect(BlockState blockState, double velocityX, double velocityY, double velocityZ, double rotationSpeed, double gravity, double drag, int duration, double scaleIn, double scaleOut) implements ParticleEffect {

    private static final RisingBlockParticleEffect DEFAULT_OPTIONS = new RisingBlockParticleEffect(
            Blocks.OBSIDIAN.getDefaultState(),
            0.0f,
            0.5f,
            0.0f,
            1.0f,
            0.0f,
            0.99f,
            100,
            10.0f,
            10.0f
    );

    public static final Codec<RisingBlockParticleEffect> CODEC = Codec.unit(DEFAULT_OPTIONS);

    public static final Factory<RisingBlockParticleEffect> SERIALIZER = new Factory<>() {

        @Override
        public RisingBlockParticleEffect read(ParticleType<RisingBlockParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            return DEFAULT_OPTIONS;
        }

        @Override
        public RisingBlockParticleEffect read(ParticleType<RisingBlockParticleEffect> type, PacketByteBuf buffer) {
            return new RisingBlockParticleEffect(
                    Block.STATE_IDS.get(buffer.readVarInt()),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readInt(),
                    buffer.readDouble(),
                    buffer.readDouble()
            );
        }
    };

    @Override
    public ParticleType<?> getType() {
        return OmegaParticles.RISING_BLOCK;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(Block.STATE_IDS.getRawId(this.blockState));
        buf.writeDouble(velocityX);
        buf.writeDouble(velocityY);
        buf.writeDouble(velocityZ);
        buf.writeDouble(rotationSpeed);
        buf.writeDouble(gravity);
        buf.writeDouble(drag);
        buf.writeInt(duration);
        buf.writeDouble(scaleIn);
        buf.writeDouble(scaleOut);
    }

    @Override
    public String asString() {
        return Registry.PARTICLE_TYPE.getId(this.getType()).toString();
    }
}
