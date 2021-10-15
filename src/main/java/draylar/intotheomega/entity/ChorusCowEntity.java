package draylar.intotheomega.entity;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ChorusCowEntity extends CowEntity implements Shearable {

    private static final int REQUIRED_SHEARED_TICKS = 20 * 60 * 3; // 3 minutes required for chorus to regrow, at a minimum
    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(ChorusCowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int shearedTicks = 0;

    public ChorusCowEntity(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            shearedTicks++;

            // Only check for shear updates every second
            if(age % 20 == 0) {
                if(shearedTicks >= REQUIRED_SHEARED_TICKS && world.random.nextInt(1000) == 0) {
                    setSheared(false);
                }
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldStack = player.getStackInHand(hand);

        if(FabricToolTags.SHEARS.contains(heldStack.getItem()) && isShearable()) {
            sheared(SoundCategory.PLAYERS);

            if (!this.world.isClient) {
                heldStack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
            }

            return ActionResult.success(this.world.isClient);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public float getSoundPitch() {
        return this.isBaby() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + .5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0F;
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0F, 0.5F);

        if (!this.world.isClient()) {
            ((ServerWorld) this.world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.CHORUS_FLOWER.getDefaultState()), this.getX(), this.getBodyY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            setSheared(true);

            // Drop 2-5 Chorus Fruit on ground
            for(int i = 0; i < 2 + world.random.nextInt(4); ++i) {
                this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getBodyY(1.0D), this.getZ(), new ItemStack(Items.CHORUS_FRUIT)));
            }
        }
    }

    @Override
    public boolean isShearable() {
        return isAlive() && !isSheared();
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(SHEARED, false);
    }

    public boolean isSheared() {
        return dataTracker.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        dataTracker.set(SHEARED, sheared);
    }
}
