package draylar.intotheomega.mixin.entity.player;

import com.mojang.authlib.GameProfile;
import draylar.intotheomega.api.data.player.PlayerDataAttachment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerDataAttachmentMixin extends PlayerEntity {

    private ServerPlayerDataAttachmentMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Inject(method = "copyFrom", at = @At("RETURN"))
    private void ito$copyPlayerDataAttachments(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        for (var entry : PlayerDataAttachment.getAll().entrySet()) {
            PlayerDataAttachment data = entry.getValue();

            // value persists between death/world changes
            if(data.retentionStrategy() == PlayerDataAttachment.RetentionStrategy.PERSIST) {
                Object oldValue = oldPlayer.getPlayerData(data);
                if(oldValue != null && oldValue != data.defaultValue()) {
                    setPlayerData(data, oldValue);
                }
            }
        }
    }
}
