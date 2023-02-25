package draylar.intotheomega.item.matrix;

import draylar.intotheomega.entity.void_matrix.beam.VoidMatrixBeamEntity;
import draylar.intotheomega.impl.AttackingItem;
import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BeneathCrystaliaItem extends Item implements AttackingItem {

    public BeneathCrystaliaItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HitResult raycast = user.raycast(100, 0, false);
        if(!world.isClient && !raycast.getType().equals(HitResult.Type.MISS)) {
            VoidMatrixBeamEntity beam = new VoidMatrixBeamEntity(OmegaEntities.VOID_MATRIX_BEAM, world);
            beam.updatePosition(raycast.getPos().getX(), raycast.getPos().getY(), raycast.getPos().getZ());
            world.spawnEntity(beam);
            user.getItemCooldownManager().set(this, 20);
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void attack(PlayerEntity player, World world, ItemStack stack) {
        if (player.getItemCooldownManager().isCoolingDown(this)) {
            return;
        }

        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            Vec3d origin = new Vec3d(player.getX(), player.getEyeY() - .5, player.getZ());
            HitResult res = player.raycast(25, 0, false);
            shootParticleBeam(serverWorld, player, origin, res.getPos());

            // Shoot from the player's right and left shoulders.
            Vec3d right = origin.add(new Vec3d(origin.getZ(), origin.getY(), -origin.getX()).normalize().multiply(5));
            Vec3d left = origin.add(new Vec3d(- origin.getZ(), origin.getY(), origin.getX()).normalize().multiply(5));
            shootParticleBeam(serverWorld, player, right, res.getPos());
            shootParticleBeam(serverWorld, player, left, res.getPos());
        }
    }

    public void shootParticleBeam(ServerWorld world, PlayerEntity player, Vec3d origin, Vec3d target) {
        double mult = .25;

        // Calculate positioning data
        double diff = Math.sqrt(origin.squaredDistanceTo(target)); // distance in blocks from origin to target
        Vec3d per = target.subtract(origin).multiply(1 / diff).multiply(mult); // position to increment per each block
        Vec3d cur = new Vec3d(origin.getX(), origin.getY(), origin.getZ()); // current position

        // only hit each entity once
        List<UUID> hitEntities = new ArrayList<>();

        for (double i = 0; i < diff; i += mult) {
            // Spawn particles along path
            world.spawnParticles(ParticleTypes.ENCHANT, cur.getX(), cur.getY(), cur.getZ(), 5, 0, 0, 0, .1);
            cur = cur.add(per);

            // Damage entities
            world.getEntitiesByClass(LivingEntity.class, new Box(cur.add(-.5, -.5, -.5), cur.add(.5, .5, .5)), entity -> !hitEntities.contains(entity.getUuid())).forEach(entity -> {
                entity.damage(DamageSource.player(player), 5); // todo: this is true damage
                // todo: power should impact damage ^^
                world.spawnParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + .5, entity.getZ(), 25, 0, 0, 0, .1);
                hitEntities.add(entity.getUuid());
            });
        }

        player.getItemCooldownManager().set(this, 15); // 15 tick cd

        // Play sound
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, .5f, 1.0F / (world.random.nextFloat() * 0.4F));
    }
}