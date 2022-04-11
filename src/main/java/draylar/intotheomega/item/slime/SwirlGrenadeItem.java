package draylar.intotheomega.item.slime;

import draylar.intotheomega.entity.SwirlGrenadeEntity;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SwirlGrenadeItem extends Item {

    public SwirlGrenadeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            SwirlGrenadeEntity grenade = new SwirlGrenadeEntity(OmegaEntities.SWIRL_GRENADE, world);
            grenade.updatePosition(user.getX(), user.getEyeY(), user.getZ());
            grenade.setVelocity(user, user.getPitch(), user.getYaw(), 0, 1.0f, 0.1f);
            world.spawnEntity(grenade);
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
