package draylar.intotheomega.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface DamageHandler {

    /**
     * Called when a {@link PlayerEntity} damages a {@link LivingEntity} while wearing or holding this tool.
     *
     * <p>
     * Available cases where an Item is passed event information includes:
     * <ul>
     *     <li>Item is a Trinket while player damages a mob</li>
     *     <li>Item is a main-hand slot while player damages a mob</li>
     * </ul>
     *  @param world world the attack occurred in
     * @param holder player attacking the target
     * @param stack relevant item in use while the damage occurred
     * @param target the entity being hit by this item
     * @param finalDamageCache
     */
    void onDamageTarget(World world, PlayerEntity holder, ItemStack stack, LivingEntity target, float finalDamageCache);
}
