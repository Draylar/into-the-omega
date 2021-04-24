package draylar.intotheomega.item.spike;

import draylar.intotheomega.api.RaycastUtils;
import draylar.intotheomega.client.SoundUtils;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.stream.Collectors;

public class DarkFlameBlasterItem extends Item {

    public DarkFlameBlasterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int remaining = stack.getMaxDamage() - stack.getDamage();

        if(!world.isClient) {
            Vec3d look = user.getRotationVector();
            Vec3d origin = user.getCameraPosVec(0);

            // check positions in front of player
            for(int i = 0; i < 100; i++) {
                origin = origin.add(look);

                // was an entity found
                BlockPos pos = new BlockPos(origin);
                Optional<LivingEntity> hit = world.getEntitiesByClass(LivingEntity.class, new Box(pos.add(-1, -1, -1), pos.add(1, 1, 1)), entity -> !entity.equals(user)).stream().findFirst();
                if(hit.isPresent()) {
                    LivingEntity target = hit.get();
                    boolean damage = target.damage(DamageSource.mobProjectile(null, user), getAttackDamage(stack, target.getGroup()));

                    if(damage) {
                        ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, target.getX(), target.getY(), target.getZ(), 50, target.getWidth() / 2, target.getHeight() / 2, target.getWidth() / 2, .05);
                    }

                    break;
                }
            }

            // damage weapon
            stack.damage(1, user, p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        } else {
            SoundUtils.play(SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, -10.0f,  0.25f);
        }

        if(remaining > 0) {
            return TypedActionResult.success(stack);
        }

        return super.use(world, user, hand);
    }

    public float getAttackDamage(ItemStack stack, EntityGroup against) {
        return 25.0f + EnchantmentHelper.getAttackDamage(stack, against);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity.age % 20 == 0) {
            stack.setDamage(Math.max(0, stack.getDamage() - 1));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
