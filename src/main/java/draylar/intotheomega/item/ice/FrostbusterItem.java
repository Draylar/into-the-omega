package draylar.intotheomega.item.ice;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FrostbusterItem extends ToolItem {

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public FrostbusterItem(ToolMaterial material, Settings settings) {
        super(material, settings);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 13.0D, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.0f, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 15000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        CompoundTag frostbusterTag = user.getStackInHand(hand).getOrCreateSubTag("Frostbuster");
        if(!frostbusterTag.contains("Charge") || frostbusterTag.getInt("Charge") <= 0) {
            user.setCurrentHand(hand);
        }

        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(!world.isClient) {
            if(remainingUseTicks <= this.getMaxUseTime(stack) - 20) {
                CompoundTag frostbusterTag = stack.getOrCreateSubTag("Frostbuster");
                frostbusterTag.putInt("Charge", 96);

                // spawn particles from player's location
                Vec3d rotation = user.getRotationVector();
                Vec3d origin = user.getPos().add(0, user.getHeight() - 1, 0);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.PLAYERS, 1.0f, 0.0f);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0f, 2.0f);

                for(double i = 0; i <= 100; i++) {
                    origin = origin.add(rotation);
                    ((ServerWorld) world).spawnParticles(OmegaParticles.SMALL_BLUE_OMEGA_BURST, origin.getX(), origin.getY(), origin.getZ(), 5, .1, .1, .1, 0);
                    ((ServerWorld) world).spawnParticles(OmegaParticles.ICE_FLAKE, origin.getX(), origin.getY(), origin.getZ(), 5, .1, .1, .1, 0);
                }
            }
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient) {
            CompoundTag frostbusterTag = stack.getOrCreateSubTag("Frostbuster");

            if(frostbusterTag.contains("Charge")) {
                frostbusterTag.putInt("Charge", Math.max(0, frostbusterTag.getInt("Charge") - 1));
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, it -> it.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0F) {
            stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    @Override
    public boolean isEffectiveOn(BlockState state) {
        return BlockTags.ICE.contains(state.getBlock());
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }
}
