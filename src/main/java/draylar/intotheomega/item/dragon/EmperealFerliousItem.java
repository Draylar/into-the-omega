package draylar.intotheomega.item.dragon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EmperealFerliousItem extends FerliousItem {

    public EmperealFerliousItem(Settings settings) {
        super(settings);
        setStaticVelocity(true);
    }

    @Override
    public void shootArrow(ItemStack arrowStack, ItemStack bowStack, World world, PlayerEntity playerEntity, ArrowData data, float pullProgress) {
        // normal arrow
        PersistentProjectileEntity arrow = createArrow(arrowStack, bowStack, world, playerEntity, data);
        arrow.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 3.0F, 1.0F);
        playerEntity.world.spawnEntity(arrow);

        // left arrow
        arrow = createArrow(arrowStack, bowStack, world, playerEntity, data);
        arrow.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw() - 15, 0.0F, pullProgress * 3.0F, 1.0F);
        playerEntity.world.spawnEntity(arrow);

        // right arrow
        arrow = createArrow(arrowStack, bowStack, world, playerEntity, data);
        arrow.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw() + 15, 0.0F, pullProgress * 3.0F, 1.0F);
        playerEntity.world.spawnEntity(arrow);
    }
}
