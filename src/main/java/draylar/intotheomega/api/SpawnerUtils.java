package draylar.intotheomega.api;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;

public class SpawnerUtils {

    public static NbtCompound createCompound(EntityType<?> type) {
        NbtCompound compound = new NbtCompound();
        compound.putString("id", Registry.ENTITY_TYPE.getId(type).toString());
        return compound;
    }
}
