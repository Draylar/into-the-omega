package draylar.intotheomega.entity;

import com.google.common.collect.ImmutableList;
import draylar.intotheomega.entity.ai.HumanoidAttackGoal;
import draylar.intotheomega.registry.OmegaItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssalKnightEntity extends HostileEntity {

    private static final List<Item> HELMETS = ImmutableList.<Item>builder().add(Items.IRON_HELMET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET, OmegaItems.PEARL_HELMET).build();
    private static final List<Item> CHESTPLATES = ImmutableList.<Item>builder().add(Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE, OmegaItems.PEARL_CHESTPLATE).build();
    private static final List<Item> LEGGINGS = ImmutableList.<Item>builder().add(Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS, OmegaItems.PEARL_LEGGINGS).build();
    private static final List<Item> BOOTS = ImmutableList.<Item>builder().add(Items.IRON_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS, OmegaItems.PEARL_BOOTS).build();
    private static final List<Item> WEAPONS = ImmutableList.<Item>builder().add(Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD, OmegaItems.PEARL_SWORD).build();

    public AbyssalKnightEntity(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(2, new HumanoidAttackGoal(this, 1.0, false));
        goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        goalSelector.add(8, new LookAroundGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createAbyssalKnightAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0);
    }

    @Override
    public void tick() {
        super.tick();

        if(world.isClient) {
//            world.addParticle(ParticleTypes.ENCHANTED_HIT, getX() + Math.sin(age), getY(), getZ() + Math.cos(age), 0, 1, 0);
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        initEquipment(difficulty);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);

        equipStack(EquipmentSlot.HEAD, new ItemStack(HELMETS.get(random.nextInt(HELMETS.size()))));
        equipStack(EquipmentSlot.CHEST, new ItemStack(CHESTPLATES.get(random.nextInt(CHESTPLATES.size()))));
        equipStack(EquipmentSlot.LEGS, new ItemStack(LEGGINGS.get(random.nextInt(LEGGINGS.size()))));
        equipStack(EquipmentSlot.FEET, new ItemStack(BOOTS.get(random.nextInt(BOOTS.size()))));

        ItemStack mainHandStack = new ItemStack(WEAPONS.get(random.nextInt(WEAPONS.size())));
        if(world.getDifficulty() == Difficulty.HARD) {
            EnchantmentHelper.enchant(world.random, mainHandStack, 15, true);
        }

        equipStack(EquipmentSlot.MAINHAND, mainHandStack);
    }

    @Override
    public void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {

    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_END_PORTAL_SPAWN;
    }
}
