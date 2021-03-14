package draylar.intotheomega.mixin.armor;

import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityGravityMixin extends Entity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow protected boolean jumping;

    public LivingEntityGravityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;"),
            index = 2)
    private double applyAbyssalBootFalling(double fall) {
        if(getVelocity().y <= 0.0D && !jumping) {
            if(getEquippedStack(EquipmentSlot.FEET).getItem().equals(OmegaItems.ABYSS_WALKERS)) {
                this.fallDistance = 0.0F;
                return 0;
            }
        }

        return fall;
    }
}
