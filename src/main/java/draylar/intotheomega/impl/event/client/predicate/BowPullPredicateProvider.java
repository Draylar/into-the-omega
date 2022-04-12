package draylar.intotheomega.impl.event.client.predicate;

import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BowPullPredicateProvider implements UnclampedModelPredicateProvider {

    @Override
    public float unclampedCall(ItemStack itemStack, @Nullable ClientWorld world, @Nullable LivingEntity livingEntity, int seed) {
        if (livingEntity == null) {
            return 0.0F;
        } else {
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (float)(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        }
    }
}
