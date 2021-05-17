package draylar.intotheomega.mixin.stance;

import draylar.intotheomega.api.client.Stance;
import draylar.intotheomega.api.client.Stances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @Inject(
            method = "method_30155", at = @At("HEAD"), cancellable = true)
    private void modifyLeftArmPose(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof PlayerEntity) {
            if (Stances.get((PlayerEntity) entity) != Stances.NONE) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "method_30154", at = @At("HEAD"), cancellable = true)
    private void modifyRightArmPose(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof PlayerEntity) {
            Stance stance = Stances.get((PlayerEntity) entity);
            stance.load((PlayerEntity) entity, (BipedEntityModel) (Object) this);

            // only continue to vanilla behavior if the stance is NONE
            if (stance != Stances.NONE) {
                ci.cancel();
            }
        }
    }
}
