package draylar.intotheomega.item.slime;

import draylar.intotheomega.entity.SlimefallEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SlimefallItem extends Item {

    public SlimefallItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
        int useTicks = getMaxUseTime(stack) - remainingUseTicks;

        if(!world.isClient && useTicks % 10 == 0) {
            HitResult raycast = user.raycast(32, 0, true);
            Vec3d pos = raycast.getPos().add(0, 32, 0);

            // Spawn Slimefall
            Vec3d offset = pos.add(world.random.nextInt(7) - 4, 0, world.random.nextInt(7) - 4);
            SlimefallEntity slime = new SlimefallEntity(OmegaEntities.SLIMEFALL, world);
            slime.updatePosition(offset.getX(), offset.getY(), offset.getZ());
            world.spawnEntity(slime);
            ((ServerWorld) world).spawnParticles(ParticleTypes.ITEM_SLIME, offset.getX(), offset.getY(), offset.getZ(), 20, 5, 5, 5, 0);
            world.playSound(null, offset.getX(), offset.getY(), offset.getZ(), SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.PLAYERS, 3.0f, 1.0f);
            stack.damage(1, user, (u) -> u.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

            if(user instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(this, 20 * 2);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return Integer.MAX_VALUE;
    }
}
