package draylar.intotheomega.mixin;

import com.mojang.authlib.GameProfile;
import draylar.intotheomega.entity.OmegaSlimeMountEntity;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityRidingMixin extends PlayerEntity {

    @Shadow public Input input;

    @Shadow private boolean riding;

    private ClientPlayerEntityRidingMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(
            method = "tickRiding",
            at = @At("RETURN"))
    private void tickRidingLogic(CallbackInfo ci) {
        if (this.getVehicle() instanceof OmegaSlimeMountEntity) {
            OmegaSlimeMountEntity boatEntity = (OmegaSlimeMountEntity) this.getVehicle();
            boatEntity.setInputs(this.input.pressingLeft, this.input.pressingRight, this.input.pressingForward, this.input.pressingBack);
            this.riding |= this.input.pressingLeft || this.input.pressingRight || this.input.pressingForward || this.input.pressingBack;
        }
    }
}
