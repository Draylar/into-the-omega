package draylar.intotheomega.api;

import net.minecraft.entity.LivingEntity;

public interface BlockEntityNotifiable {
    void notify(LivingEntity from);
}
