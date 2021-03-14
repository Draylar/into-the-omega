package draylar.intotheomega.item;

import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class OmegaDaggerItem extends SwordItem {

    public OmegaDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && hand == Hand.MAIN_HAND) {
            HitResult rayTrace = user.raycast(16, 0, false);
            Vec3d pos = rayTrace.getPos();
            user.requestTeleport(pos.getX(), pos.getY(), pos.getZ());

            // play sounds
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F));
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 0.25F, 0.0f);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.05F, -5F);

            // play effects
            ((ServerWorld) world).spawnParticles(OmegaParticles.OMEGA_PARTICLE, pos.getX(), pos.getY(), pos.getZ(), 50, 1, 1, 1, 1);

            // hit nearby mobs at position
            world.getEntitiesByClass(LivingEntity.class, new Box(pos.add(-5, -5, -5), pos.add(5, 5, 5)), entity -> entity instanceof Monster).forEach(entity -> {
                // damage entity
                entity.damage(DamageSource.player(user), 2);

                // knock entity back
                Vec3d diff = entity.getPos().subtract(user.getPos()).multiply(.25).add(0, .2, 0);
                entity.setVelocity(diff);
            });

            // set cd
            user.getItemCooldownManager().set(this, 20 * 5);

            // give user buffs
//            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10 * 5, 0));
        }

        return super.use(world, user, hand);
    }
}
