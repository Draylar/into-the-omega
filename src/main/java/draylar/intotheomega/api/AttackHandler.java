package draylar.intotheomega.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface AttackHandler {
    void onAttack(World world, PlayerEntity holder, ItemStack stack, LivingEntity target);
}
