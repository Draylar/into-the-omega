package draylar.intotheomega.mixin;

import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MobSpawnerLogic.class)
public interface MobSpawnerLogicAccessor {

    @Accessor
    void setMinSpawnDelay(int minSpawnDelay);

    @Accessor
    void setMaxSpawnDelay(int maxSpawnDelay);

    @Accessor
    void setSpawnCount(int spawnCount);

    @Accessor
    void setMaxNearbyEntities(int maxNearbyEntities);

    @Accessor
    void setRequiredPlayerRange(int requiredPlayerRange);

    @Accessor
    void setSpawnRange(int spawnRange);

    @Accessor
    DataPool<MobSpawnerEntry> getSpawnPotentials();

    @Accessor
    void setSpawnPotentials(DataPool<MobSpawnerEntry> spawnPotentials);

    @Invoker
    void callUpdateSpawns(World world, BlockPos pos);
}
