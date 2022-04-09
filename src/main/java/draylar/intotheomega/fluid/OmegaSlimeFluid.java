package draylar.intotheomega.fluid;

import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class OmegaSlimeFluid extends FlowableFluid {

    @Override
    public Fluid getFlowing() {
        return OmegaFluids.OMEGA_SLIME_FLOWING;
    }

    @Override
    public Fluid getStill() {
        return OmegaFluids.OMEGA_SLIME_STILL;
    }

    @Override
    public Item getBucketItem() {
        return Items.DIAMOND;
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    public void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    public int getFlowSpeed(WorldView worldView) {
        return 1;
    }

    @Override
    public int getLevelDecreasePerBlock(WorldView worldView) {
        return 1;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    @Override
    public int getTickRate(WorldView worldView) {
        return 40;
    }

    @Override
    public float getBlastResistance() {
        return 50;
    }

    @Override
    public BlockState toBlockState(FluidState fluidState) {
        return OmegaBlocks.OMEGA_SLIME_FLUID.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(fluidState));
    }

    @Override
    public ParticleEffect getParticle() {
        return ParticleTypes.ITEM_SLIME;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == OmegaFluids.OMEGA_SLIME_FLOWING || fluid == OmegaFluids.OMEGA_SLIME_STILL;
    }

    public static class Flowing extends OmegaSlimeFluid {

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }
    }

    public static class Still extends OmegaSlimeFluid {

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }
    }
}
