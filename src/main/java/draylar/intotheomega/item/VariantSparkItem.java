package draylar.intotheomega.item;

import draylar.intotheomega.api.item.AttackHandler;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

// NBT 0: Void Tide Vanquisher
// NBT 1: Grand Spark Buster
public class VariantSparkItem extends Item implements AttackHandler {

    public VariantSparkItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(user.isSneaking()) {
           if(!world.isClient) {
               int mode = stack.getOrCreateSubNbt("Data").getInt("Mode");
               stack.getOrCreateSubNbt("Data").putInt("Mode", mode == 0 ? 1 : 0);

               // sound

               // particles
               for(int i = 0; i < 100; i++) {
                   ((ServerWorld) world).spawnParticles(OmegaParticles.VARIANT_FUSION, user.getX(), user.getY() + .5, user.getZ(), 1, 1 - world.random.nextInt(3), 0, 1 - world.random.nextInt(3), 0);
               }
           }

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        int mode = stack.getOrCreateSubNbt("Data").getInt("Mode");
        tooltip.add(new TranslatableText(String.format("item.intotheomega.variant_spark.%d", mode)).formatted(Formatting.GRAY));
    }

    @Override
    public Text getName(ItemStack stack) {
        int mode = stack.getOrCreateSubNbt("Data").getInt("Mode");
        return new TranslatableText(this.getTranslationKey(stack)).formatted(mode == 0 ? Formatting.BLUE : Formatting.LIGHT_PURPLE);
    }

//    @Override
//    public Multimap<EntityAttribute, EntityAttributeModifier> getDynamicModifiers(EquipmentSlot slot, ItemStack stack, @Nullable LivingEntity user) {
//        if(slot.equals(EquipmentSlot.MAINHAND)) {
//            int mode = stack.getOrCreateSubNbt("Data").getInt("Mode");
//            ImmutableSetMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableSetMultimap.builder();
//
//            if(mode == 1) {
//                builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 29, EntityAttributeModifier.Operation.ADDITION));
//                builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2f, EntityAttributeModifier.Operation.ADDITION));
//            } else {
//                builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 14, EntityAttributeModifier.Operation.ADDITION));
//                builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -3f, EntityAttributeModifier.Operation.ADDITION));
//            }
//            return builder.build();
//        }
//
//        return DynamicAttributeTool.super.getDynamicModifiers(slot, stack, user);
//    }


    @Override
    public void onAttack(World world, PlayerEntity holder, ItemStack stack, LivingEntity target) {
        if(!world.isClient) {
            stack.damage(1, holder, playerEntity -> playerEntity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

            // If the mode of this Variant Spark is "Void Tide Vanquisher," chain-attack nearby enemies.
            int mode = stack.getOrCreateSubNbt("Data").getInt("Mode");
            if(mode == 0) {
                List<HostileEntity> chained = new ArrayList<>();
                LivingEntity last = target;

                // chain up to 5 mobs
                for(int i = 0; i < 5; i++) {
                    Entity finalLast = last;
                    List<HostileEntity> found = world.getEntitiesByClass(HostileEntity.class, new Box(last.getBlockPos().add(-16, -8, -16), last.getBlockPos().add(16, 8, 16)), entity -> !chained.contains(entity) && entity.canSee(finalLast));
                    @Nullable HostileEntity closest = world.getClosestEntity(found, TargetPredicate.DEFAULT, target, last.getX(), last.getY(), last.getZ());

                    // chain into the next mob
                    if(closest != null) {
                        // draw particles from previous to new
                        double distance = last.distanceTo(closest);
                        Vec3d source = last.getPos();
                        Vec3d diff = closest.getPos().subtract(last.getPos()).normalize().multiply(.1);
                        for(double itr = 0; itr < distance; itr += 0.1) {
                            ((ServerWorld) world).spawnParticles(OmegaParticles.SMALL_BLUE_OMEGA_BURST, source.getX(), source.getY() + 1, source.getZ(), 1, 0, 0, 0, 0);
                            source = source.add(diff);
                        }

                        // damage & store
                        last = closest;
                        last.damage(DamageSource.player(holder), EnchantmentHelper.getAttackDamage(stack, last.getGroup()) + 15);
                        chained.add(closest);
                    }
                }
            } else {
                ((ServerWorld) world).spawnParticles(OmegaParticles.SMALL_PINK_OMEGA_BURST, target.getX(), target.getY() + 1, target.getZ(), 10, .1, .3, .1, .1);
            }
        }
    }
}
