package draylar.intotheomega.mixin.entity;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.api.SpawnHandling;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerSpawnMixin {

    @Shadow private ClientWorld world;

    @Inject(
            method = "onEntitySpawn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;getEntityTypeId()Lnet/minecraft/entity/EntityType;"), cancellable = true)
    private void handleCustomSpawning(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        EntityType<?> type = packet.getEntityTypeId();

        // Only handle for ITO entities
        if(Registry.ENTITY_TYPE.getId(type).getNamespace().equals(IntoTheOmega.MODID)) {
            Entity entity = type.create(world);
            if(entity instanceof SpawnHandling) {
                ((SpawnHandling) entity).onSpawnPacket(packet);

                // add entity & do not continue processing
                world.addEntity(packet.getId(), entity);
                ci.cancel();
            }
        }
    }
}
