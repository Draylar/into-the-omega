package draylar.intotheomega.api.block;

import net.minecraft.entity.LivingEntity;

public interface BlockEntityNotifiable {
    void notify(LivingEntity from);
}
