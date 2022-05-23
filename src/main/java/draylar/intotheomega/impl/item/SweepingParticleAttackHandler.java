package draylar.intotheomega.impl.item;

import draylar.intotheomega.api.item.AttackHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public interface SweepingParticleAttackHandler extends AttackHandler {

    @Override
    default void onAttack(World world, PlayerEntity holder, ItemStack stack, LivingEntity target) {
        double x = -MathHelper.sin(holder.getYaw() * ((float) Math.PI / 180));
        double z = MathHelper.cos(holder.getYaw() * ((float) Math.PI / 180));
        if (holder.world instanceof ServerWorld) {
            spawnAttackParticles(((ServerWorld) holder.world), holder, stack, x, z);
        }
    }

    default void spawnAttackParticles(ServerWorld world, PlayerEntity holder, ItemStack stack, double xOffset, double zOffset) {
        world.spawnParticles(getAttackParticleType(), holder.getX() + xOffset, holder.getBodyY(0.5), holder.getZ() + zOffset, 0, xOffset, 0.0, zOffset, 0.0);
    }

    default DefaultParticleType getAttackParticleType() {
        return ParticleTypes.SWEEP_ATTACK;
    }
}
