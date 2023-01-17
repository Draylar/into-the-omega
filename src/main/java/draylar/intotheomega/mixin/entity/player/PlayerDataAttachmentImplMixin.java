package draylar.intotheomega.mixin.entity.player;

import draylar.intotheomega.api.data.player.PlayerDataAccess;
import draylar.intotheomega.api.data.player.PlayerDataAttachment;
import draylar.intotheomega.registry.OmegaServerPackets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerDataAttachmentImplMixin extends Entity implements PlayerDataAccess {

    private final Map<PlayerDataAttachment<?>, Object> values = new HashMap<>();

    private PlayerDataAttachmentImplMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public <T> void setPlayerData(PlayerDataAttachment<T> type, T value) {
        values.put(type, value);
    }

    @Override
    public <T> T getPlayerData(PlayerDataAttachment<T> type) {
        return (T) Objects.requireNonNullElse(values.get(type), type.defaultValue());
    }

    @Override
    public <T> void resetPlayerData(PlayerDataAttachment<T> type) {
        values.put(type, type.defaultValue());
    }

    @Override
    public <T> void incrementPlayerData(PlayerDataAttachment<T> type) {
        T defaultValue = type.defaultValue();
        if(defaultValue instanceof Integer defaultValueInt) {
            values.putIfAbsent(type, defaultValueInt);
            values.put(type, Math.min((int) type.range().max(), (int) values.get(type) + 1));
        }
    }

    @Override
    public <T> void incrementPlayerData(PlayerDataAttachment<T> type, T amount) {
        T defaultValue = type.defaultValue();
        if(defaultValue instanceof Integer defaultValueInt) {
            values.putIfAbsent(type, defaultValueInt);
            values.put(type, Math.min((int) type.range().max(), (int) values.get(type) + (int) amount));
        }
    }

    @Unique
    public <T> void sync(PlayerDataAttachment<T> type) {
        if(!world.isClient) {
            OmegaServerPackets.syncPlayerDataAttachment(type, (ServerPlayerEntity) (Object) this);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void ito$writePlayerDataAttachments(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("ITOPlayerData", PlayerDataAttachment.write((PlayerEntity) (Object) this, values));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void ito$readPlayerDataAttachments(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound root = nbt.getCompound("ITOPlayerData");
        PlayerDataAttachment.read((PlayerEntity) (Object) this, root);
    }
}
