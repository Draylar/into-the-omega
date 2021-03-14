package draylar.intotheomega.item;

import draylar.intotheomega.entity.OmegaSlimeMountEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SlimeMountItem extends Item {

    public SlimeMountItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && hand == Hand.MAIN_HAND && !user.hasVehicle()) {
            OmegaSlimeMountEntity mount = new OmegaSlimeMountEntity(OmegaEntities.OMEGA_SLIME_MOUNT, world);
            mount.setPos(user.getX(), user.getY(), user.getZ());
            mount.updateTrackedPosition(user.getX(), user.getY(), user.getZ());
            mount.requestTeleport(user.getX(), user.getY(), user.getZ());
            world.spawnEntity(mount);

            user.startRiding(mount);
        }

        return super.use(world, user, hand);
    }
}
