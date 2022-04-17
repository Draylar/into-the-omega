package draylar.intotheomega.item.dragon;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class SecondFusionItem extends Item {

    public SecondFusionItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(!world.isClient) {
            ServerWorld sWorld = (ServerWorld) world;

            Vec3d rotationVector = user.getRotationVector();
            Vec3d start = user.getPos().add(0, user.getHeight() * .7, 0);
            Vec3d current = start.add(0, 0, 0);
            double distance = 10;
            Set<Entity> hit = new HashSet<>();

            for(double i = 0; i <= distance; i += .25) {
                current = current.add(rotationVector.multiply(.25f));
                sWorld.spawnParticles(OmegaParticles.VARIANT_FUSION, current.getX(), current.getY(), current.getZ(), 3, 0, .025f, 0, 0);

                hit.addAll(world.getOtherEntities(user, new Box(current.getX() - .25f, current.getY() - .25f, current.getZ() - .25f, current.getX() + .25f, current.getY() + .25f, current.getZ() + .25f), entity -> {
                    return !(entity instanceof WolfEntity);
                }));
            }

            hit.forEach(entity -> {
                entity.damage(DamageSource.mob(user), 10.0f);
            });
        }

//        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.PLAYERS, .25f, 1.5f);
        super.usageTick(world, user, stack, remainingUseTicks);
    }
}
