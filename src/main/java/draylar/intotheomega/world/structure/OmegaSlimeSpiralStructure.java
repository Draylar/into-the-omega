package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.StructureStartCache;
import draylar.intotheomega.impl.StructurePieceExtensions;
import draylar.intotheomega.registry.world.OmegaStructurePieces;
import draylar.intotheomega.world.SlimeStructureGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.SimpleRandom;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OmegaSlimeSpiralStructure extends StructureFeature<DefaultFeatureConfig> {

    private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

    public OmegaSlimeSpiralStructure(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec, StructureGeneratorFactory.simple(StructureGeneratorFactory.checkForBiomeOnTop(Heightmap.Type.WORLD_SURFACE_WG), OmegaSlimeSpiralStructure::addPieces));
    }

    public static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 0, context.chunkPos().getStartZ());
        collector.addPiece(new OmegaSlimeSpiralVoid(blockPos));
        collector.addPiece(new OmegaSlimeSpiralPiece(blockPos));
    }

    public static class OmegaSlimeSpiralVoid extends StructurePiece {

        public OmegaSlimeSpiralVoid(BlockPos pos) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL_VOID, 0,
                    new BlockBox(pos.getX() - 128, pos.getY() - 64, pos.getZ() - 128, pos.getX() + 128, pos.getY() + 250, pos.getZ() + 128));

            setOrientation(null);
        }

        public OmegaSlimeSpiralVoid(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL_VOID, compound);
            setOrientation(null);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            for(int x = -128; x <= 128; x++) {
                for (int z = -128; z <= 128; z++) {

                    // todo: add y to calculation inside loop once we have cached the air for performance
                    double noise = NOISE.sample((pos.getX() + x) / 50f, pos.getY(), (pos.getZ() + z) / 50f) * 28;
                    if(Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) <= 128 - noise) {
                        for (int y = 0; y < 70; y++) {
                            addBlock(world, Blocks.AIR.getDefaultState(), pos.getX() + x, y, pos.getZ() + z, chunkBox);
                        }
                    }
                }
            }
        }
    }

    public static class OmegaSlimeSpiralPiece extends StructurePiece {

        public OmegaSlimeSpiralPiece(BlockPos pos) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL, 0,
                    new BlockBox(pos.getX() - 48, pos.getY() - 48, pos.getZ() - 48, pos.getX() + 48, pos.getY() + 250, pos.getZ() + 48));



            setOrientation(null);
        }

        public OmegaSlimeSpiralPiece(StructureContext context, NbtCompound compound) {
            super(OmegaStructurePieces.OMEGA_SLIME_SPIRAL, compound);
            setOrientation(null);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {

        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
            long start = System.currentTimeMillis();
            @Nullable Map<BlockPos, BlockState> cache = StructureStartCache.get(StructurePieceExtensions.get(this).getStructureStart()).getPlacementCache();

            if(cache == null) {
                cache = new HashMap<>();
                new SlimeStructureGenerator(this, chunkBox, cache).spawn(world, pos);
                StructureStartCache.get(StructurePieceExtensions.get(this).getStructureStart()).setPlacementCache(cache);
            } else {
                cache.forEach((cachePos, state) -> {
                    addBlock(world, state, cachePos.getX(), cachePos.getY(), cachePos.getZ(), chunkBox);
                });
            }

//            System.out.println("Spiral: " + (System.currentTimeMillis() - start));
        }
    }
}
