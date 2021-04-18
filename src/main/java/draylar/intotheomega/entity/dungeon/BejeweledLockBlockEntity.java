package draylar.intotheomega.entity.dungeon;

import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BejeweledLockBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable {

    private int unlockTicks = -1;

    public BejeweledLockBlockEntity() {
        super(OmegaEntities.BEJEWELED_LOCK);
    }

    @Override
    public void tick() {
        assert world != null;

        // ??
        if(getPos() == null) {
            return;
        }

        if(!world.isClient) {
            ServerWorld sWorld = (ServerWorld) world;

            // Tick the unlock counter.
            if (unlockTicks >= 0) {
                sync();

                // break blocks
                if(unlockTicks == 0) {
                    // replace bejeweled obsidian 2 blocks up
                    world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
                    world.setBlockState(pos.up(2), Blocks.AIR.getDefaultState());

                    for(Direction direction : Arrays.stream(Direction.values()).filter(direction -> !direction.getAxis().equals(Direction.Axis.Y)).collect(Collectors.toList())) {
                        world.setBlockState(pos.offset(direction).down(), Blocks.AIR.getDefaultState());
                        world.setBlockState(pos.offset(direction).offset(direction.rotateYClockwise()).down(), Blocks.AIR.getDefaultState());
                    }
                }

                // play grinding sound
                if(unlockTicks % 2 == 0) {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0f, -5.0f);
                }

                unlockTicks++;
            }

            // If the counter is >= 100, remove this lock.
            if (unlockTicks > 360) {
                world.setBlockState(pos, OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState());
                world.setBlockState(pos.up(), OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState());
                world.setBlockState(pos.up(2), OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState());
                markRemoved();
            }
        }
    }

    public void unlock() {
        unlockTicks = 0;
    }

    public int getUnlockTicks() {
        return Math.max(0, unlockTicks);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.unlockTicks = tag.getInt("UnlockTicks");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("UnlockTicks", unlockTicks);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(getCachedState(), tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }
}
