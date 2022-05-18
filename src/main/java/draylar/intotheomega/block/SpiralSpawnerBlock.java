package draylar.intotheomega.block;

import draylar.intotheomega.api.SpawnerUtils;
import draylar.intotheomega.impl.MobSpawnerLogicExtensions;
import draylar.intotheomega.mixin.MobSpawnerLogicAccessor;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.dynamic.Range;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SpiralSpawnerBlock extends BlockWithEntity {

    public SpiralSpawnerBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        MobSpawnerBlockEntity spawner = new MobSpawnerBlockEntity(pos, state);
        MobSpawnerLogicAccessor accessor = (MobSpawnerLogicAccessor) spawner.getLogic();
        accessor.setMaxSpawnDelay(200);
        accessor.setMinSpawnDelay(75);
        accessor.setRequiredPlayerRange(32);
        accessor.setSpawnCount(8);
        accessor.setSpawnRange(18);

        ((MobSpawnerLogicExtensions) spawner.getLogic()).setSkipRead();

        MobSpawnerEntry.CustomSpawnRules rule = new MobSpawnerEntry.CustomSpawnRules(
                new Range<>(0, 15),
                new Range<>(0, 15));

        DataPool<MobSpawnerEntry> spawns = DataPool.<MobSpawnerEntry>builder()
                .add(new MobSpawnerEntry(SpawnerUtils.createCompound(OmegaEntities.ABYSSAL_KNIGHT), Optional.of(rule)), 1)
                .add(new MobSpawnerEntry(SpawnerUtils.createCompound(OmegaEntities.ENTWINED), Optional.of(rule)), 1)
                .add(new MobSpawnerEntry(SpawnerUtils.createCompound(EntityType.ENDERMAN), Optional.of(rule)), 1)
                .build();

        accessor.setSpawnPotentials(spawns);

        spawner.getLogic().setSpawnEntry(null, pos, spawns.getEntries().get(0).getData());
        spawner.markDirty();
        return spawner;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return SpawnerBlock.checkType(type, BlockEntityType.MOB_SPAWNER, world.isClient ? MobSpawnerBlockEntity::clientTick : MobSpawnerBlockEntity::serverTick);
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        int i = 15 + world.random.nextInt(15) + world.random.nextInt(15);
        this.dropExperience(world, pos, i);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
}
