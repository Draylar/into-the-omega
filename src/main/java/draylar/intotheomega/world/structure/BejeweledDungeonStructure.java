package draylar.intotheomega.world.structure;

import com.mojang.serialization.Codec;
import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaStructurePieces;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.List;
import java.util.Random;

public class BejeweledDungeonStructure extends StructureFeature<DefaultFeatureConfig> {

    public BejeweledDungeonStructure(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean isUniformDistribution() {
        return false;
    }

    @Override
    public boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        return getGenerationHeight(i, j, chunkGenerator) >= 60;
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) {
        Random random = new Random(chunkX + chunkZ * 10387313);
        BlockRotation rotation = BlockRotation.random(random);

        int xOffset = 5;
        int zOffset = 5;
        if (rotation == BlockRotation.CLOCKWISE_90) {
            xOffset = -5;
        } else if (rotation == BlockRotation.CLOCKWISE_180) {
            xOffset = -5;
            zOffset = -5;
        } else if (rotation == BlockRotation.COUNTERCLOCKWISE_90) {
            zOffset = -5;
        }

        int x = (chunkX << 4) + 7;
        int z = (chunkZ << 4) + 7;
        int m = chunkGenerator.getHeightInGround(x, z, Heightmap.Type.WORLD_SURFACE_WG);
        int n = chunkGenerator.getHeightInGround(x, z + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
        int o = chunkGenerator.getHeightInGround(x + xOffset, z, Heightmap.Type.WORLD_SURFACE_WG);
        int p = chunkGenerator.getHeightInGround(x + xOffset, z + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
        return Math.min(Math.min(m, n), Math.min(o, p));
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {

        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
            super(structureFeature, i, j, blockBox, k, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            BlockRotation blockRotation = BlockRotation.random(this.random);
            int genHeight = getGenerationHeight(chunkX, chunkZ, chunkGenerator) - 10;

            if (genHeight >= 20) {
                BlockPos blockPos = new BlockPos(chunkX * 16 + 8, genHeight, chunkZ * 16 + 8);
                Generator.addPieces(structureManager, blockPos, blockRotation, this.children, this.random);
                this.setBoundingBoxFromChildren();
            }
        }
    }

    public static class Generator {

        private static final Identifier TEMPLATE = IntoTheOmega.id("bejeweled_dungeon");
        private static final Identifier METADATA = IntoTheOmega.id("loot");

        public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {
            pieces.add(new draylar.intotheomega.world.generator.MatrixPedestalGenerator.Piece(manager, TEMPLATE, pos, rotation));
        }

        public static class Piece extends SimpleStructurePiece {

            private final Identifier template;
            private final BlockRotation rotation;

            public Piece(StructureManager manager, Identifier template, BlockPos pos, BlockRotation rotation) {
                super(OmegaStructurePieces.BEJEWELED_DUNGEON, 0);
                this.template = template;
                this.rotation = rotation;
                this.pos = pos;

                initializeStructureData(manager);
            }

            public Piece(StructureManager manager, CompoundTag tag) {
                super(OmegaStructurePieces.BEJEWELED_DUNGEON, tag);
                template = new Identifier(tag.getString("Template"));
                rotation = BlockRotation.valueOf(tag.getString("Rot"));
                initializeStructureData(manager);
            }

            private void initializeStructureData(StructureManager manager) {
                Structure structure = manager.getStructureOrBlank(this.template);
                StructurePlacementData structurePlacementData = new StructurePlacementData().setRotation(rotation).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
                this.setStructureData(structure, this.pos, structurePlacementData);
            }

            @Override
            protected void toNbt(CompoundTag tag) {
                super.toNbt(tag);
                tag.putString("Template", template.toString());
                tag.putString("Rot", rotation.name());
            }

            @Override
            protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess access, Random random, BlockBox boundingBox) {
                if (metadata.equals(METADATA.toString())) {
                    LootableContainerBlockEntity.setLootTable(access, random, pos.down(), LootTables.ABANDONED_MINESHAFT_CHEST);
                }
            }
        }
    }
}
