package draylar.intotheomega.impl.event.client.predicate;

import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class VariantSparkModePredicateProvider implements UnclampedModelPredicateProvider {

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
        return stack.getOrCreateSubNbt("Data").getInt("Mode");
    }
}
