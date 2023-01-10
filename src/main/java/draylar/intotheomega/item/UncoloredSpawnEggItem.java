package draylar.intotheomega.item;

import draylar.intotheomega.mixin.access.SpawnEggItemAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.SpawnEggItem;

public class UncoloredSpawnEggItem extends SpawnEggItem {

    public UncoloredSpawnEggItem(EntityType<? extends MobEntity> type, Settings settings) {
        super(type, 0, 0, settings);
        SpawnEggItemAccessor.getSPAWN_EGGS().remove(type);
    }
}
