package draylar.intotheomega.mixin;

import draylar.intotheomega.registry.OmegaEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {

    @Shadow private ItemStack tridentStack;

    private TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Allows Omega Channeling to summon thunder in rainy weather (regardless of thunder).
     *
     * @param entityHitResult  the entity hit by this trident
     * @param ci               mixin callback info
     */
    @Inject(
            method = "onEntityHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isThundering()Z"),
            cancellable = true
    )
    private void triggerOmegaChanneling(EntityHitResult entityHitResult, CallbackInfo ci) {
        // check if the trident has omega channeling
        if (this.world instanceof ServerWorld && EnchantmentHelper.getLevel(OmegaEnchantments.CHANNELING, this.tridentStack) > 0) {
            BlockPos blockPos = entityHitResult.getEntity().getBlockPos();

            // check for standard channeling limitations
            if (this.world.isSkyVisible(blockPos)) {
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this.world);
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                lightningEntity.setChanneler(this.getOwner() instanceof ServerPlayerEntity ? (ServerPlayerEntity) this.getOwner() : null);
                this.world.spawnEntity(lightningEntity);

                // play sound & cancel to prevent double thunder
                this.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, 5.0f, 1.0F);
                ci.cancel();
            }
        }
    }
}
