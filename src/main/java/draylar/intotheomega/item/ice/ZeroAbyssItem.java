package draylar.intotheomega.item.ice;

import draylar.intotheomega.api.client.Stance;
import draylar.intotheomega.api.client.StanceProvider;
import draylar.intotheomega.api.client.Stances;
import draylar.intotheomega.entity.ice.AbyssGlobeEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ZeroAbyssItem extends SwordItem implements StanceProvider {

    public ZeroAbyssItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20 * 2 + 1;
    }

    @Override
    public Stance getUseStance(ItemStack stack) {
        return Stances.SWORD_DOWN;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int ticksUsed = getMaxUseTime(stack) - remainingUseTicks;

        if(ticksUsed < 20 * 2) {
            user.playSound(SoundEvents.BLOCK_SNOW_PLACE, 1.0f, ticksUsed / 40f);
        } else if (ticksUsed == 20 * 2) {
            user.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 3.0f, -1.0f);
            user.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 3.0f, 1.0f);
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient) {
            if(user instanceof PlayerEntity) {
                AbyssGlobeEntity globe = new AbyssGlobeEntity(OmegaEntities.ABYSS_GLOBE, world);
                globe.requestTeleport(user.getX(), user.getY(), user.getZ());
                globe.setOwner(user.getUuid());
                world.spawnEntity(globe);
                ((PlayerEntity) user).getItemCooldownManager().set(this, 20 * 5);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}
