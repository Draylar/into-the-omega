package draylar.intotheomega.mixin;

import draylar.intotheomega.registry.OmegaEntityGroups;
import draylar.intotheomega.registry.OmegaTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "getGroup",
            at = @At("HEAD"),
            cancellable = true
    )
    private void addCustomEntityGroups(CallbackInfoReturnable<EntityGroup> cir) {
        if(getType().isIn(OmegaTags.END_CREATURE)) {
            cir.setReturnValue(OmegaEntityGroups.END_CREATURE);
        }

        if(getType().isIn(OmegaTags.SLIME_CREATURE)) {
            cir.setReturnValue(OmegaEntityGroups.SLIME_CREATURE);
        }
    }
}
