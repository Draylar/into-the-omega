package draylar.intotheomega.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface AttackHandler {

    /**
     * Called when a {@link PlayerEntity} attacks a {@link LivingEntity} with this tool.
     *
     * @param world world the attack occurred in
     * @param holder player attacking the target
     * @param stack item held by the player implementing this interface
     * @param target the entity being hit by this item
     */
    void onAttack(World world, PlayerEntity holder, ItemStack stack, LivingEntity target);
}
