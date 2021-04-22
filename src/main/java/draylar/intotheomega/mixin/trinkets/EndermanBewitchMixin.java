package draylar.intotheomega.mixin.trinkets;

import draylar.intotheomega.impl.Bewitchable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@Mixin(EndermanEntity.class)
public class EndermanBewitchMixin implements Bewitchable {

    @Unique private UUID bewitchedUUID = null;

    @Override
    public boolean isBewitchedTo(PlayerEntity player) {
        return bewitchedUUID != null && bewitchedUUID.equals(player.getUuid());
    }

    @Override
    public boolean isBewitched() {
        return bewitchedUUID != null;
    }

    @Override
    public void setBewitched(PlayerEntity source) {
        bewitchedUUID = source.getUuid();
    }
}
