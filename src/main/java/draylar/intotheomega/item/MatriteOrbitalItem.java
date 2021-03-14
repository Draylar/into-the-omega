package draylar.intotheomega.item;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.TrinketItem;
import draylar.intotheomega.entity.matrite.MatriteEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class MatriteOrbitalItem extends TrinketItem {

    public MatriteOrbitalItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String s, String s1) {
        return s.equals(SlotGroups.HAND) && s1.equals("orbital");
    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {
        BlockPos pos = player.getBlockPos();
        ItemCooldownManager cooldownManager = player.getItemCooldownManager();
        World world = player.world;

        // Item can be used again
        if(cooldownManager.getCooldownProgress(this, 0) <= 0) {
            LivingEntity monster = null;
            float distance = Float.MAX_VALUE;

            // Locate closest hostile
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, new Box(pos.add(-16, -16, -16), pos.add(16, 16, 16)), entity -> entity instanceof Monster);
            for (LivingEntity entity : entities) {
                float entityDistance = entity.distanceTo(player);

                if (entityDistance < distance) {
                    monster = entity;
                    distance = entityDistance;
                }
            }

            // If a monster is nearby, shoot at it
            if(monster != null) {
                MatriteEntity matrite = new MatriteEntity(world);
                matrite.setTarget(monster, 20);
                matrite.setOwner(player);
                matrite.updatePosition(pos.getX(), player.getEyeY() + 1.5f, pos.getZ());
                world.spawnEntity(matrite);
                cooldownManager.set(this, 20 * 3);
            }
        }
    }
}
