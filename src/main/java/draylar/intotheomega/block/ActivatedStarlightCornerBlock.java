package draylar.intotheomega.block;

import draylar.intotheomega.registry.OmegaBlockEntities;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ActivatedStarlightCornerBlock extends BlockWithEntity {

    public ActivatedStarlightCornerBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Entity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        world.addParticle(OmegaParticles.TINY_STAR, pos.getX() + 0.5, pos.getY() + 9.75, pos.getZ() + 0.5, world.random.nextDouble(0.1) - 0.05, 0, world.random.nextDouble(0.1) - 0.05);
    }

    public static class Entity extends BlockEntity {

        public Entity(BlockPos pos, BlockState state) {
            super(OmegaBlockEntities.STARLIGHT_CORNER, pos, state);
        }
    }
}
