package draylar.intotheomega.world.api;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseIslandStructure extends StructureFeature<DefaultFeatureConfig> {

    public static final List<Class<? extends BaseIslandStructure>> ALL_ISLANDS = new ArrayList<>();
    private static OpenSimplex2F NOISE = null;

    public BaseIslandStructure(Codec<DefaultFeatureConfig> codec, StructurePiecesGenerator<DefaultFeatureConfig> generator, Class<? extends BaseIslandStructure> islandClass) {
        super(codec, StructureGeneratorFactory.simple(context -> canGenerate(context, islandClass), generator));
        ALL_ISLANDS.add(getClass());
    }

    private static boolean canGenerate(StructureGeneratorFactory.Context<DefaultFeatureConfig> context, Class<? extends BaseIslandStructure> islandClass) {
        if(NOISE == null) {
            NOISE = new OpenSimplex2F(context.seed());
        }

        ChunkPos chunkPos = context.chunkPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;

        // No islands should spawn within 1,000 blocks of the main island.
        if(Math.sqrt(Math.pow(chunkX * 16, 2) + Math.pow(chunkZ * 16, 2)) <= 1_000) {
            return false;
        }

        // For now, Island structures should only spawn on ChunkPosses divisible by 5.
        // TODO: Use the noise type kdot talked about for spreading out islands.

        // When an island attempts to spawn, the general "all islands" map is polled.
        // A random value is selected, and only the island that fits that value will win.
        // All islands will return true for the grid/area selection, but only 1 will return true for this method.
        if(chunkX % 25 == 0 && chunkZ % 25 == 0) {
            double noise = NOISE.noise2(chunkX, chunkZ);
            int index = ALL_ISLANDS.indexOf(islandClass);

            // In a collection 3 islands, the indexes are 0, 1, and 2.
            // We add 1 to the noise (which is from [-1, 1]) and divide by 2 to get [0, 1].
            // The noise value is then multiplied by the size of all values. In this case, we would get a value between [0, 3].
            // We then round the noise value down to get (0, 1, 2). The element at that index in the array is selected to be valid.
            noise = (noise + 1) / 2; // => [0, 1]
            noise = noise * ALL_ISLANDS.size();
            int searchIndex = (int) Math.floor(noise);
            return index == searchIndex;
        }

        return false;
    }

    public abstract String getId();
}
