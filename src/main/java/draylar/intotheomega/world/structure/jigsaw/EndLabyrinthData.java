package draylar.intotheomega.world.structure.jigsaw;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import draylar.intotheomega.IntoTheOmega;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;

public class EndLabyrinthData {

    public static final Identifier EMPTY = new Identifier("empty");
    private static final Identifier TERMINATORS = IntoTheOmega.id("end_labyrinth/terminators");

    public static final RegistryEntry<StructurePool> START_POOL = StructurePools.register(new StructurePool(
            IntoTheOmega.id("end_labyrinth/centers"),
            new Identifier("empty"),
            ImmutableList.of(
                    Pair.of(StructurePoolElement.ofSingle("intotheomega:end_labyrinth/centers/labyrinth_center"), 1)),
            StructurePool.Projection.RIGID));

    public static final RegistryEntry<StructurePool> HALLWAY_POOL = StructurePools.register(new StructurePool(
            IntoTheOmega.id("end_labyrinth/hallways"),
            TERMINATORS,
            ImmutableList.of(
                    Pair.of(StructurePoolElement.ofSingle("intotheomega:end_labyrinth/hallways/hallway_medium"), 1)),
            StructurePool.Projection.RIGID));

    public static final RegistryEntry<StructurePool> ROOM_POOL = StructurePools.register(new StructurePool(
            IntoTheOmega.id("end_labyrinth/rooms"),
            TERMINATORS,
            ImmutableList.of(
                    Pair.of(StructurePoolElement.ofSingle("intotheomega:end_labyrinth/rooms/junction"), 1),
                    Pair.of(StructurePoolElement.ofSingle("intotheomega:end_labyrinth/rooms/medium"), 1)),
            StructurePool.Projection.RIGID));

    public static final RegistryEntry<StructurePool> TERMINATOR_POOL = StructurePools.register(new StructurePool(
            TERMINATORS,
            EMPTY,
            ImmutableList.of(
                    Pair.of(StructurePoolElement.ofSingle("intotheomega:end_labyrinth/terminators/wall"), 1),
                    Pair.of(StructurePoolElement.ofSingle("intotheomega:end_labyrinth/rooms/medium"), 5)),
            StructurePool.Projection.RIGID));

    public static void init() {

    }
}
