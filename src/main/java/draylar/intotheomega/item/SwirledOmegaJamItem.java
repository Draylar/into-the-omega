package draylar.intotheomega.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SwirledOmegaJamItem extends Item {

    public SwirledOmegaJamItem(Settings settings) {
        super(settings.maxDamage(10));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient && entity.age % 20 == 0) {
            if(entity instanceof ServerPlayerEntity) {
                int needed = stack.getDamage();
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                int i = player.inventory.remove(s -> s.getItem().equals(Items.SLIME_BALL), needed, player.inventory);
                stack.setDamage(stack.getDamage() - i);
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.canConsume(this.getFoodComponent().isAlwaysEdible()) && itemStack.getDamage() < itemStack.getMaxDamage()) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), user.getEatSound(stack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);

        if(user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + 1));

            player.getHungerManager().eat(stack.getItem(), stack);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
        }

        return stack;
    }

    @Override
    public boolean isFood() {
        return true;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 24;
    }

    @Nullable
    @Override
    public FoodComponent getFoodComponent() {
        return new FoodComponent.Builder().hunger(4).saturationModifier(4).build();
    }
}
