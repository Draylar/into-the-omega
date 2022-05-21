package draylar.intotheomega.world;

import draylar.intotheomega.api.DevelopmentSpawnable;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.random.SimpleRandom;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public record NovaZoneGenerator(@Nullable StructurePiece piece, @Nullable BlockBox box, @Nullable Map<BlockPos, BlockState> cache) implements DevelopmentSpawnable {

    private static final SimplexNoiseSampler NOISE = new SimplexNoiseSampler(new SimpleRandom(0));

    @Override
    public void spawn(StructureWorldAccess world, BlockPos pos) {
        spawnPlate(world, pos);
    }

    public void spawnPlate(WorldAccess world, BlockPos pos) {
        for (int x = -115; x <= 115; x++) {
            for (int z = -115; z <= 115; z++) {
                double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                if(distance <= 115) {
                    if(distance <= 105) {
                        set(world, new BlockPos(pos.getX() + x, pos.getY() + (distance / 16), pos.getZ() + z), Blocks.OBSIDIAN.getDefaultState());
                    } else {
                        set(world, new BlockPos(pos.getX() + x, pos.getY() + (distance / 16), pos.getZ() + z), Blocks.CRYING_OBSIDIAN.getDefaultState());
                    }

                    int down = Math.max(5, Math.min(pos.getY(), 115 - (int) distance));
                    for (int i = 1; i < down; i++) {
                        set(world, new BlockPos(pos.getX() + x, pos.getY() + (distance / 16) - i, pos.getZ() + z), Blocks.OBSIDIAN.getDefaultState());
                    }
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
