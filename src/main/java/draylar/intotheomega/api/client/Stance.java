package draylar.intotheomega.api.client;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;

public interface Stance {
    void load(PlayerEntity player, BipedEntityModel<PlayerEntity> model);
}
