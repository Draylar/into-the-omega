package draylar.intotheomega.item;

import draylar.intotheomega.entity.VioletUnionBladeEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VioletUnionItem extends Item {

    private static final String DATA_KEY = "Data";
    private static final String CHARGE_KEY = "Charge";

    public VioletUnionItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(getCharge(stack) > 0) {
            if (!world.isClient) {
                decrementCharge(stack);

                // fire!
                Vec3d target = user.raycast(32, 0, false).getPos();

                // spawn violet union blades
                for(int i = 0; i < 10; i++) {
                    VioletUnionBladeEntity blade = new VioletUnionBladeEntity(OmegaEntities.VIOLET_UNION_BLADE, world);
                    double x = user.getX() + 8 - world.random.nextInt(16);
                    double y = user.getY() + 5 + world.random.nextInt(5);
                    double z = user.getZ() + 8 - world.random.nextInt(16);
//                    blade.requestTeleport(x, y, z);
//                    blade.setPos(x, y, z);

                    blade.updatePosition(x, y, z);
                    // calc diff from new pos to target, then apply normalized velocity to blade
                    Vec3d change = blade.getPos().subtract(target).normalize().multiply(-1);
//                    blade.setVelocity(change.getX(), change.getY(), change.getZ());
                    blade.setProperties(user, user.pitch, user.yaw, 0.0F, 1, 1);
                    blade.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES,  target);
                    blade.velocityDirty = true;
                    blade.velocityModified = true;
                    world.spawnEntity(blade);
                }
            }

            return TypedActionResult.success(stack);
        }

        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        // Attempt to initialize tag for charge rendering
        if(stack.getTag() == null || !stack.getTag().contains(DATA_KEY)) {
            stack.getOrCreateSubTag(DATA_KEY).putInt(CHARGE_KEY, 5);
        }

        // Recharge
        if(entity.age % 10 == 0) {
            incrementCharge(stack);
        }
    }

    public int getCharge(ItemStack stack) {
        // If the tag does not exist, initialize it with a charge of 5
        if(stack.getTag() == null || !stack.getTag().contains(DATA_KEY)) {
            stack.getOrCreateSubTag(DATA_KEY).putInt(CHARGE_KEY, 5);
            return 5;
        } else {
            return stack.getOrCreateSubTag(DATA_KEY).getInt(CHARGE_KEY);
        }
    }

    public void decrementCharge(ItemStack stack) {
        int charge = getCharge(stack);
        stack.getOrCreateSubTag(DATA_KEY).putInt(CHARGE_KEY, Math.max(0, charge - 1));
    }

    public void incrementCharge(ItemStack stack) {
        int charge = getCharge(stack);
        stack.getOrCreateSubTag(DATA_KEY).putInt(CHARGE_KEY, Math.min(5, charge + 1));
    }
}
