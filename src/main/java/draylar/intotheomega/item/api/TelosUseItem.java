package draylar.intotheomega.item.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class TelosUseItem extends Item {

    public TelosUseItem(Settings settings) {
        super(settings);
    }

    @Override
    public final TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        if(OmegaComponents.TELOS.get(user).getTelos() >= getTelosCost()) {
//            TypedActionResult<ItemStack> result = telosUse(world, user, hand);
//
//            // If the use-action passes, take Telos from the user
//            if(result.getResult().isAccepted() && !world.isClient) {
//                OmegaComponents.TELOS.get(user).revoke(getTelosCost());
//            }
//
//            return result;
//        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public abstract TypedActionResult<ItemStack> telosUse(World world, PlayerEntity user, Hand hand);

    public abstract int getTelosCost();
}
