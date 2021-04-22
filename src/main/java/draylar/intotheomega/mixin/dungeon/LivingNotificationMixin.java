package draylar.intotheomega.mixin.dungeon;

import draylar.intotheomega.api.BlockEntityNotifiable;
import draylar.intotheomega.api.EntityDeathNotifier;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingNotificationMixin extends Entity implements EntityDeathNotifier {

    @Unique private RegistryKey<World> targetWorld;
    @Unique private BlockPos target = null;

    public LivingNotificationMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void setTarget(@NotNull RegistryKey<World> world, @NotNull BlockPos target) {
        this.targetWorld = world;
        this.target = target;
    }

    @Inject(
            method = "onDeath",
        at =  @At("RETURN"))
    private void notifyOnDeath(DamageSource source, CallbackInfo ci) {
        if(!world.isClient && targetWorld != null && target != null) {
            MinecraftServer server = ((ServerWorld) world).getServer();
            ServerWorld targetWorld = server.getWorld(this.targetWorld);

            if(targetWorld != null) {
                BlockEntity targetBE = targetWorld.getBlockEntity(target);
                if(targetBE instanceof BlockEntityNotifiable) {
                    ((BlockEntityNotifiable) targetBE).notify((LivingEntity) (Object) this);
                }
            }
        }
    }
}
