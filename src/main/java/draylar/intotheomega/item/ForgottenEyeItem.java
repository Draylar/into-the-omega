package draylar.intotheomega.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ForgottenEyeItem extends Item {

    public ForgottenEyeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            SnowballEntity proj = new SnowballEntity(world, user);
            proj.setItem(new ItemStack(Items.ENDER_EYE));
            proj.setProperties(user, user.pitch, user.yaw, 0, 2, 0);
            world.spawnEntity(proj);
        }

        return super.use(world, user, hand);
    }
}
