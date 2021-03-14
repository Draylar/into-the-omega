package draylar.intotheomega.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ExplosiveDoubleJumpItem extends DoubleJumpTrinketItem {

    public ExplosiveDoubleJumpItem(Settings settings, int descriptionLines) {
        super(settings, descriptionLines);
    }

    @Override
    public void onDoubleJump(World world, PlayerEntity player, ItemStack stack) {
        if(world.isClient) {
            world.createExplosion(player, player.getX(), player.getY(), player.getZ(), 2, Explosion.DestructionType.NONE);
        }
    }
}
