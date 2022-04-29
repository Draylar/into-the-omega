package draylar.intotheomega.item.starfall;

import draylar.intotheomega.impl.DoubleJumpTrinket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class StardustWingsItem extends Item implements DoubleJumpTrinket {

    public StardustWingsItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getDoubleJumps(ItemStack stack) {
        return 10;
    }

    @Override
    public void onDoubleJump(World world, PlayerEntity player, ItemStack stack) {
        player.addVelocity(player.getRotationVector().getX() / 2, 1.0f, player.getRotationVector().getZ() / 2);
        player.velocityDirty = true;
        player.velocityModified = true;

        // SFX
        for(int i = 0; i < 15; i++) {
            world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), world.random.nextDouble(1.0) - 0.5, 0, world.random.nextDouble(1.0) - 0.5);
        }
    }
}
