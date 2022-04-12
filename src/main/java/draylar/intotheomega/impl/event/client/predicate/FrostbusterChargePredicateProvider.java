package draylar.intotheomega.impl.event.client.predicate;

import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FrostbusterChargePredicateProvider implements UnclampedModelPredicateProvider {

    @Override
    public float unclampedCall(ItemStack itemStack, @Nullable ClientWorld world, @Nullable LivingEntity livingEntity, int seed) {
        if (livingEntity == null) {
            return 0.0F;
        } else {
            return (float)(96 - itemStack.getOrCreateSubNbt("Frostbuster").getInt("Charge")) / 6;
        }
    }
}
