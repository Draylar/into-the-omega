package draylar.intotheomega.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.intotheomega.impl.ProjectileManipulator;
import draylar.intotheomega.item.CustomBowItem;
import draylar.intotheomega.item.ExplosiveFocusItem;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PersistentProjectileEntity.class)
public abstract class ArrowMixin extends ProjectileEntity implements ProjectileManipulator {

    private static final TrackedData<ItemStack> ORIGIN_STACK = DataTracker.registerData(PersistentProjectileEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private Vec3d ito_posContext = null;
    private int ito_iteration = 0;

    private ArrowMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void storeContext(CallbackInfo ci, boolean bl, Vec3d vec, double x, double y, double z, int i) {
        this.ito_posContext = new Vec3d(x, y, z);
        this.ito_iteration = i;
    }

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", ordinal = 0)
    )
    private void changeParticles(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        ItemStack sourceStack = dataTracker.get(ORIGIN_STACK);
        double d = ito_posContext.x;
        double e = ito_posContext.y;
        double g = ito_posContext.z;

        // Check if the source bow has a preferred particle to display
        if(!sourceStack.isEmpty() && sourceStack.getItem() instanceof CustomBowItem) {
            CustomBowItem bow = (CustomBowItem) sourceStack.getItem();
            ParticleEffect bowParticles = bow.getArrowParticles();

            if(bowParticles != null) {
                this.world.addParticle(bowParticles, this.getX() + d * (double) ito_iteration / 4.0D, this.getY() + e * (double) ito_iteration / 4.0D, this.getZ() + g * (double) ito_iteration / 4.0D, -d, -e + 0.2D, -g);
                return;
            }
        }

        // Display default crit particles
        this.world.addParticle(ParticleTypes.CRIT, this.getX() + d * (double) ito_iteration / 4.0D, this.getY() + e * (double) ito_iteration / 4.0D, this.getZ() + g * (double) ito_iteration / 4.0D, -d, -e + 0.2D, -g);
    }

    @Override
    public void ito_setOrigin(ItemStack stack) {
        dataTracker.set(ORIGIN_STACK, stack);
    }

    @Override
    public ItemStack ito_getOrigin() {
        return dataTracker.get(ORIGIN_STACK);
    }

    @Inject(
            method = "initDataTracker",
            at = @At("RETURN")
    )
    private void addDataTrackers(CallbackInfo ci) {
        dataTracker.startTracking(ORIGIN_STACK, ItemStack.EMPTY);
    }

    @Inject(
            method = "onBlockHit",
            at = @At("RETURN")
    )
    private void onBlockHit(BlockHitResult blockHitResult, CallbackInfo ci) {
        if(!world.isClient && getOwner() instanceof PlayerEntity player) {
            TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
                if(component.isEquipped(stack -> stack.getItem() instanceof ExplosiveFocusItem)) {
                    player.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1, Explosion.DestructionType.NONE);
                }
            });
        }
    }
    @Inject(
            method = "onEntityHit",
            at = @At("RETURN")
    )
    private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        if(!world.isClient && getOwner() instanceof PlayerEntity player) {
            TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
                if(component.isEquipped(stack -> stack.getItem() instanceof ExplosiveFocusItem)) {
                    player.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1, Explosion.DestructionType.NONE);
                }
            });
        }
    }

    @ModifyVariable(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;isTouchingWater()Z"),
            index = 22)
    private float cancelVelocityDecrement(float velocityModifier) {
        Item originItem = ito_getOrigin().getItem();

        if(originItem instanceof CustomBowItem) {
            if(((CustomBowItem) originItem).hasStaticVelocity()) {
                return 1.0F;
            }
        }

        return velocityModifier;
    }

    // TODO: non-invasive way to do this?
    @Override
    public boolean hasNoGravity() {
        if(ito_getOrigin().getItem().equals(OmegaItems.FERLIOUS)) {
            return true;
        }

        return super.hasNoGravity();
    }
}
