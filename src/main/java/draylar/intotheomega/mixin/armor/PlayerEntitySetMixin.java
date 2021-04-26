package draylar.intotheomega.mixin.armor;

import draylar.intotheomega.item.api.SetArmorItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntitySetMixin extends LivingEntity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    private PlayerEntitySetMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if(!world.isClient) {
            // If the player is wearing an armor set, apply the status effects of it to them.
            ItemStack helmet = getEquippedStack(EquipmentSlot.HEAD);
            if(helmet.getItem() instanceof SetArmorItem) {
                SetArmorItem set = (SetArmorItem) helmet.getItem();
                if(set.hasFullSet(this)) {
                   set.getEffects().forEach(this::addStatusEffect);
                }
            }
        }
    }
}
