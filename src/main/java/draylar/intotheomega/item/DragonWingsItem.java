package draylar.intotheomega.item;

import draylar.intotheomega.IntoTheOmega;
import draylar.intotheomega.impl.DoubleJumpTrinket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DragonWingsItem extends WingsItem implements DoubleJumpTrinket {

    private static final Identifier DRAGON_WING_TEXTURE = IntoTheOmega.id("textures/item/dragon_wings.png");

    public DragonWingsItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public int getDoubleJumps(ItemStack stack) {
        return 5;
    }

    @Override
    public void onDoubleJump(World world, PlayerEntity player, ItemStack stack) {
        player.addVelocity(player.getRotationVector().getX() / 2, 1.0f, player.getRotationVector().getZ() / 2);
        player.velocityDirty = true;
        player.velocityModified = true;

        // SFX
        for (int i = 0; i < 50; i++) {
            double x = player.getX() + world.random.nextDouble(4) - 2;
            double z = player.getZ() + world.random.nextDouble(4) - 2;
            world.addParticle(ParticleTypes.DRAGON_BREATH, x, player.getY(), z, 0, 0, 0);
        }
    }

    @Override
    public Identifier getTexture() {
        return DRAGON_WING_TEXTURE;
    }
}
