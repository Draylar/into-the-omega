package draylar.intotheomega.entity.block;

import draylar.intotheomega.entity.enigma.EnigmaKingEntity;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaParticles;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;

public class EnigmaStandBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable {

    private static final String ACTIVATED_KEY = "Activated";
    private static final String ACTIVATION_TICKS_KEY = "ActivationTicks";

    private boolean activated = false;
    private int activationTicks = 0;

    public EnigmaStandBlockEntity() {
        super(OmegaEntities.ENIGMA_STAND);
    }

    public void activate() {
        activated = true;
    }

    @Override
    public void tick() {
        if(world == null || world.isClient) {
            return;
        }

        // Ticking boss spawn animation.
        if(activated) {
            if(activationTicks == 75) {
                ((ServerWorld) world).spawnParticles(
                        OmegaParticles.OMEGA_PARTICLE,
                        getPos().getX() + .5,
                        getPos().getY(),
                        getPos().getZ() + .5,
                        75,
                        .2,
                        .2,
                        .2,
                        0.5
                );

                ((ServerWorld) world).spawnParticles(
                        ParticleTypes.ENCHANTED_HIT,
                        getPos().getX() + .5,
                        getPos().getY(),
                        getPos().getZ() + .5,
                        75,
                        .2,
                        .2,
                        .2,
                        0.5
                );

                ((ServerWorld) world).spawnParticles(
                        ParticleTypes.ENCHANT,
                        getPos().getX() + .5,
                        getPos().getY(),
                        getPos().getZ() + .5,
                        75,
                        .2,
                        .2,
                        .2,
                        0.5
                );
            }

            // At 5 seconds, remove this Enigma Stand.
            if(activationTicks >= 80) {
                ((ServerWorld) world).spawnParticles(
                        OmegaParticles.DARK,
                        getPos().getX() + .5,
                        getPos().getY(),
                        getPos().getZ() + .5,
                        50,
                        .2,
                        .2,
                        .2,
                        0.5
                );

                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                markRemoved();

                // entity
                EnigmaKingEntity enigmaKing = new EnigmaKingEntity(OmegaEntities.ENIGMA_KING, world);
                enigmaKing.requestTeleport(pos.getX() + .5f, pos.getY(), pos.getZ() + .5f);
                world.spawnEntity(enigmaKing);
                return;
            }

            // sound FX
            if(activationTicks == 1) {
                world.playSound(null, getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else if (activationTicks == 20) {
                world.playSound(null, getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 0.5f);
            } else if (activationTicks == 40) {
                world.playSound(null, getPos(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, 1.0f, 0.5f);
            } else if (activationTicks == 75) {
                world.playSound(null, getPos(), SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT, SoundCategory.BLOCKS, 0.5f, 0.5f);
            }


            activationTicks++;
            sync();
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putBoolean(ACTIVATED_KEY, activated);
        tag.putInt(ACTIVATION_TICKS_KEY, activationTicks);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.activated = tag.getBoolean(ACTIVATED_KEY);
        this.activationTicks = tag.getInt(ACTIVATION_TICKS_KEY);
    }

    public int getActivationTicks() {
        return activationTicks;
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(getCachedState(), tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }
}
