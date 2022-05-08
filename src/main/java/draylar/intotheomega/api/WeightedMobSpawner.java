package draylar.intotheomega.api;

import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WeightedMobSpawner {

    static {
        EntryList firstFloor = new EntryList()
                .add(new Entry(100,
                        new SingleMobEntry<>(OmegaEntities.ENTWINED),
                        new SingleMobEntry<>(OmegaEntities.ENTWINED),
                        new SingleMobEntry<>(OmegaEntities.ENTWINED)))
                .add(new Entry(100,
                        new SingleMobEntry<>(OmegaEntities.ENTWINED),
                        new SingleMobEntry<>(OmegaEntities.ENTWINED),
                        new SingleMobEntry<>(OmegaEntities.ENTWINED)));
    }

    public WeightedMobSpawner() {

    }

    public static class EntryList {

        private final List<Entry> entries = new ArrayList<>();

        public EntryList add(Entry entry) {
            entries.add(entry);
            return this;
        }

        private Queue<Entry> createQueue() {
            return new ArrayDeque<>(entries);
        }
    }

    public static class Entry {

        private final int weight;
        private final List<SingleMobEntry<?>> entries;

        public Entry(int weight, SingleMobEntry<?>... entries) {
            this.weight = weight;
            this.entries = Arrays.asList(entries);
        }
    }

    public static class SingleMobEntry<T extends Entity> {

        @NotNull private final EntityType<T> type;
        @Nullable private final NbtCompound entityNbt;
        @Nullable private final Vec3d spawnOffset;

        public SingleMobEntry(@NotNull EntityType<T> type) {
            this(type, null, null);
        }

        public SingleMobEntry(@NotNull EntityType<T> type, @Nullable NbtCompound entityNbt) {
            this(type, entityNbt, null);
        }

        public SingleMobEntry(@NotNull EntityType<T> type, @Nullable NbtCompound entityNbt, @Nullable Vec3d spawnOffset) {
            this.type = type;
            this.entityNbt = entityNbt;
            this.spawnOffset = spawnOffset;
        }

        @Nullable
        public T create(ServerWorld world, Vec3d origin, int randomRadius) {
            @Nullable T created = type.create(world);
            if(created != null) {
                if(entityNbt != null) {
                    created.readNbt(entityNbt);
                }

                if(spawnOffset != null) {
                    created.updatePosition(origin.getX() + spawnOffset.getX(), origin.getY() + spawnOffset.getY(), origin.getZ() + spawnOffset.getZ());
                } else {
                    created.updatePosition(origin.getX() + world.random.nextInt(randomRadius * 2) - randomRadius, origin.getY(), origin.getZ() + world.random.nextInt(randomRadius * 2) - randomRadius);
                }
            }

            return created;
        }
    }
}
