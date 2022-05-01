package draylar.intotheomega.item.starfall;

import draylar.intotheomega.entity.starfall.StarfallProjectileEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StarfallTomeItem extends Item {

    public StarfallTomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(stack.getDamage() < 5) {
            if(!world.isClient) {
                if(!user.isCreative()) {
                    stack.setDamage(stack.getDamage() + 1);
                }

                HitResult raycast = user.raycast(64, 0, false);
                Vec3d target = raycast.getPos();
                Vec3d offset = new Vec3d(world.random.nextInt(32) - 16, 32 + world.random.nextInt(64), world.random.nextInt(32) - 16);
                Vec3d spawn = target.add(offset);

                StarfallProjectileEntity projectile = new StarfallProjectileEntity(OmegaEntities.STARFALL_PROJECTILE, world);
                projectile.updatePosition(spawn.getX(), spawn.getY(), spawn.getZ());
                projectile.setVelocity(target.subtract(spawn).normalize());
                projectile.noClip = true;
                projectile.setTargetLocation(target);
                projectile.setOwner(user);
                world.spawnEntity(projectile);

                // update cooldown
                NbtCompound cooldownNbt = stack.getOrCreateSubNbt("Cooldown");
                if(!cooldownNbt.contains("Time")) {
                    cooldownNbt.putInt("Time", 20);
                }
            }

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        NbtCompound cooldownNbt = stack.getOrCreateSubNbt("Cooldown");
        if(cooldownNbt.contains("Time")) {
            int time = cooldownNbt.getInt("Time");
            if(time <= 1) {
                cooldownNbt.remove("Time");
                stack.setDamage(stack.getDamage() - 1);
            } else {
                cooldownNbt.putInt("Time", time - 1);
            }
        }
    }
}
