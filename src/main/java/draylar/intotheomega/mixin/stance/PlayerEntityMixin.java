package draylar.intotheomega.mixin.stance;

import draylar.intotheomega.api.client.Stance;
import draylar.intotheomega.api.client.StanceAccessor;
import draylar.intotheomega.api.client.Stances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements StanceAccessor {

    @Unique
    private Stance stance = Stances.NONE;

    @Override
    @Unique
    public Stance getStance() {
        return stance;
    }

    @Override
    @Unique
    public void setStance(@NotNull Stance stance) {
        this.stance = stance;
    }
}
