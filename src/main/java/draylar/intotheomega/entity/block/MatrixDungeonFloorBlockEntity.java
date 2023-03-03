package draylar.intotheomega.entity.block;

import draylar.intotheomega.api.RandomUtils;
import draylar.intotheomega.api.block.EarlyRender;
import draylar.intotheomega.registry.OmegaBlockEntities;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.vfx.particle.option.RisingBlockParticleEffect;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;

public class MatrixDungeonFloorBlockEntity extends BlockEntity implements EarlyRender {

    public MatrixDungeonFloorBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.MATRIX_DUNGEON_FLOOR, pos, state);
    }

    public static void tickClient(World world, BlockPos blockPos, BlockState blockState, MatrixDungeonFloorBlockEntity floor) {
        Vec2f pos = RandomUtils.edge(world.random).multiply(38);
        if(world.random.nextDouble() <= 0.6) {
            return;
        }

        BlockState state = OmegaBlocks.MATRIX_BRICKS.getDefaultState();
        if(world.random.nextDouble() <= 0.1) {
            state = Blocks.END_STONE.getDefaultState();
        } else if(world.random.nextDouble() <= 0.5) {
            state = Blocks.OBSIDIAN.getDefaultState();
        }

        world.addParticle(
                new RisingBlockParticleEffect(state, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.99f, 100, 10.0f, 10.0f),
                true,
                floor.getPos().getX() + pos.x,
                floor.getPos().getY() + 3,
                floor.getPos().getZ() + pos.y,
                0.0f,
                0.0f,
                0.0f
        );
    }
}
