package draylar.intotheomega.mixin.world;

import draylar.intotheomega.biome.OmegaChunkGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {

    static {
        Registry.register(Registry.CHUNK_GENERATOR, "omega_end", OmegaChunkGenerator.CODEC);
    }
}
