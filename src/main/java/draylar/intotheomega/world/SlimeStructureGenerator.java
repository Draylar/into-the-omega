package draylar.intotheomega.world;

import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.random.SimpleRandom;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public record SlimeStructureGenerator(@Nullable StructurePiece piece, @Nullable BlockBox box, @Nullable Map<BlockPos, BlockState> cache) {

    private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

    public void spawn(WorldAccess world, BlockPos pos) {
        // Base
        for (int x = -48; x <= 48; x++) {
            for (int z = -48; z <= 48; z++) {
                double noise = NOISE.sample((pos.getX() + x) / 20f, -1000, (pos.getZ() + z) / 20f) * 16;
                if(Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) <= 48 - noise) {
                    set(world, new BlockPos(pos.getX() + x, 1, pos.getZ() + z), OmegaBlocks.CONGEALED_SLIME.getDefaultState());
                }
            }
        }
        
        for (int y = 0; y < 250; y += 2) {
            float radius = 20 + (1.0f - y / 200f) * 30;
            int x = (int) (Math.sin(y) * radius);
            int z = (int) (Math.cos(y) * radius);

            BlockPos origin = pos.add(x, y, z);
            for (BlockPos outer : BlockPos.iterateOutwards(origin, 15, 15, 15)) {
                if(Math.sqrt(outer.getSquaredDistance(origin)) <= 10 + NOISE.sample(outer.getX() / 15f, outer.getY() / 15f, outer.getZ() / 15f) * 5) {
                    set(world, outer, OmegaBlocks.CONGEALED_SLIME.getDefaultState());
                }
            }
        }

        for (int y = 0; y < 250; y += 2) {
            float radius = 20 + (1.0f - y / 200f) * 30;
            int x = (int) (Math.cos(y) * radius);
            int z = (int) (Math.sin(y) * radius);

            BlockPos origin = pos.add(x, y, z);
            for (BlockPos outer : BlockPos.iterateOutwards(origin, 15, 15, 15)) {
                if(Math.sqrt(outer.getSquaredDistance(origin)) <= 10 + NOISE.sample(outer.getX() / 15f, outer.getY() / 15f, outer.getZ() / 15f) * 5) {
                    set(world, outer, OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState());
                }
            }
        }

        // Root
        for (int x = -48; x <= 48; x++) {
            for (int z = -48; z <= 48; z++) {
                double noise = NOISE.sample((pos.getX() + x) / 20f, 1000, (pos.getZ() + z) / 20f) * 16;
                if(Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) <= 48 - noise) {
                    set(world, new BlockPos(pos.getX() + x, 195, pos.getZ() + z), OmegaBlocks.CONGEALED_OMEGA_SLIME.getDefaultState());
                }
            }
        }
    }

    private void set(WorldAccess world, BlockPos pos, BlockState state) {
        if(piece != null) {
            piece.addBlock((StructureWorldAccess) world, state, pos.getX(), pos.getY(), pos.getZ(), box);
            if(cache != null) {
                cache.put(pos.toImmutable(), state);
            }
        } else {
            world.setBlockState(pos, state, 3);
        }
    }
}
