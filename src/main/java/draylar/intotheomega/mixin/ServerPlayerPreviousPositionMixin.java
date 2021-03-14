package draylar.intotheomega.mixin;

import draylar.intotheomega.impl.PreviousPositionAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerPreviousPositionMixin extends LivingEntity implements PreviousPositionAccessor {

    @Unique
    private Vec3d ito_prevPos;

    private ServerPlayerPreviousPositionMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if(age % 5 == 0) {
            ito_prevPos = this.getPos();
        }
    }

    @Override
    public Vec3d getPreviousPosition() {
        return ito_prevPos;
    }
}
