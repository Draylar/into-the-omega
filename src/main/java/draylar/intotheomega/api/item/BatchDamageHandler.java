package draylar.intotheomega.api.item;

import draylar.intotheomega.api.BatchDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface BatchDamageHandler {

    /**
     * Called before a {@link PlayerEntity} damages multiple {@link LivingEntity}s through a batch attack while wearing or holding this tool.
     *
     * <p>
     * The provided {@link BatchDamage.BatchAttackData} instance can be mutated to tweak the batch attack details before it occurs.
     *
     * <p>
     * Available cases where an Item is passed event information includes:
     * <ul>
     *     <li>Item is a Trinket while player damages a mob</li>
     *     <li>Item is a main-hand slot while player damages a mob</li>
     * </ul>
     *  @param world world the attack occurred in
     *
     * @param holder player attacking the target
     * @param stack  relevant item in use while the damage occurred
     * @param data   metadata about the batch attack that is about to occur
     */
    default void beforeBatchDamage(World world, PlayerEntity holder, ItemStack stack, BatchDamage.BatchAttackData data) {

    }

    /**
     * Called after a {@link PlayerEntity} damages multiple {@link LivingEntity}s through a batch attack while wearing or holding this tool.
     *
     * <p>
     * Available cases where an Item is passed event information includes:
     * <ul>
     *     <li>Item is a Trinket while player damages a mob</li>
     *     <li>Item is a main-hand slot while player damages a mob</li>
     * </ul>
     *  @param world world the attack occurred in
     *
     * @param holder player attacking the target
     * @param stack  relevant item in use while the damage occurred
     * @param data   metadata about the batch attack that just took place
     */
    default void afterBatchDamage(World world, PlayerEntity holder, ItemStack stack, BatchDamage.BatchAttackData data) {

    }
}
