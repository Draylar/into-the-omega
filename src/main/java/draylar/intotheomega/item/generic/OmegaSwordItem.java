package draylar.intotheomega.item.generic;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OmegaSwordItem extends SwordItem {

    public OmegaSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            HitResult res = user.raycast(25, 0, false);

            double mult = .25;

            // Calculate positioning data
            Vec3d origin = user.getPos();
            Vec3d target = res.getPos();
            double diff = Math.sqrt(origin.squaredDistanceTo(target)); // distance in blocks from origin to target
            Vec3d per = target.subtract(origin).multiply(1 / diff).multiply(mult); // position to increment per each block
            Vec3d cur = new Vec3d(origin.getX(), origin.getY(), origin.getZ()); // current position

            // only hit each entity once
            List<UUID> hitEntities = new ArrayList<>();

            for(double i = 0; i < diff; i += mult) {
                // Spawn particles along path
                serverWorld.spawnParticles(OmegaParticles.OMEGA_PARTICLE, cur.getX(), cur.getY(), cur.getZ(), 1, 0, 0, 0, 0);
                cur = cur.add(per);

                // Damage entities
                world.getEntitiesByClass(Entity.class, new Box(cur.add(-.5, -.5, -.5), cur.add(.5, .5, .5)), entity -> entity != user && !hitEntities.contains(entity.getUuid())).forEach(entity -> {
                    entity.damage(DamageSource.player(user), getAttackDamage());
//
//                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), GOBSounds.KATANA_SWOOP, SoundCategory.PLAYERS, 2F, 1.5F + (float) world.random.nextDouble() * .5f);
//                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5F, 1.5F + (float) world.random.nextDouble() * .5f);
                    serverWorld.spawnParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + .5, entity.getZ(), 25, 0, 0, 0, .1);
                    hitEntities.add(entity.getUuid());
                });
            }

            user.getItemCooldownManager().set(this, 20 * 2); // 2 second cd

            // Play sound
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, .5f, 1.0F / (world.random.nextFloat() * 0.4F));

            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }
}
