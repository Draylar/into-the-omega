package draylar.intotheomega.mixin;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.registry.OmegaItems;
import draylar.intotheomega.registry.OmegaTags;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "getGroup",
            at = @At("HEAD"),
            cancellable = true
    )
    private void addEndGroup(CallbackInfoReturnable<EntityGroup> cir) {
        if(OmegaTags.END_CREATURE.contains(this.getType())) {
            cir.setReturnValue(IntoTheOmega.END_CREATURE);
        }
    }
}
