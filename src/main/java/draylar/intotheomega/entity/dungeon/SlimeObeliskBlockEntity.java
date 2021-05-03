package draylar.intotheomega.entity.dungeon;

import draylar.intotheomega.api.BlockEntityNotifiable;
import draylar.intotheomega.api.EntityDeathNotifier;
import draylar.intotheomega.entity.OmegaSlimeEntity;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaStatusEffects;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;

public class SlimeObeliskBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable, BlockEntityNotifiable {

    private static final int MAX_DAMAGE = 25;
    private static final List<MobSupplier> MOBS = new ArrayList<>();

    static {
        MOBS.add((world, pos, player) -> {
            OmegaSlimeEntity slime = new OmegaSlimeEntity(OmegaEntities.OMEGA_SLIME, world);
            slime.initialize(world, world.getLocalDifficulty(pos), SpawnReason.SPAWNER, null, null);
            slime.updatePosition(pos.getX(), pos.getY(), pos.getZ());
            slime.setTarget(player);
            return slime;
        });
    }

    private int damage = 0;
    private int age;
    private boolean active = true;

    public SlimeObeliskBlockEntity() {
        super(OmegaEntities.SLIME_OBELISK);
    }

    @Override
    public void tick() {
        assert world != null;

        // ??
        if(getPos() == null) {
            return;
        }

        if(!world.isClient) {
            ServerWorld sWorld = (ServerWorld) world;
            age++;

            List<PlayerEntity> players = new ArrayList<>(world.getEntitiesByClass(PlayerEntity.class, new Box(getPos().add(-16, 0, -16), getPos().add(16, 16, 16)), player -> !player.isSpectator()));
            players.forEach(player -> player.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.SWIRLED, 15, 0, true, false)));

            // Summon mobs around the pedestal if a player is nearby.
            if(age % 20 == 0 && active) {
                if(world.random.nextInt(3) == 0) {
                    // Ensure a player is nearby before spawning.
                    if(!players.isEmpty()) {
                        // Next, ensure we have not hit the local mob-cap.
                        long nearby = world.getEntitiesByClass(HostileEntity.class, new Box(getPos().add(-16, 0, -16), getPos().add(16, 16, 16)), hostile -> true).size();
                        if(nearby < 12) {
                            // Requirements have been hit. Spawn in a random mob and tell it to notify us when it dies.

                            // Attempt to find a random position nearby to spawn our mob at
                            BlockPos spawnPos = null;
                            for(int i = 0; i < 10; i++) {
                                BlockPos potentialPos = getPos().add(world.random.nextInt(32) - 16, world.random.nextInt(5), world.random.nextInt(32) - 16);
                                if(world.getBlockState(potentialPos).isAir()) {
                                    spawnPos = potentialPos;
                                    break;
                                }
                            }

                            // if a proper spawn position was found, add in our entity
                            if(spawnPos != null) {
                                MobSupplier found = MOBS.get(world.random.nextInt(MOBS.size()));
                                LivingEntity created = found.create(sWorld, spawnPos, players.get(world.random.nextInt(players.size())));
                                ((EntityDeathNotifier) created).setTarget(world.getRegistryKey(), pos);
                                world.spawnEntity(created);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.active = tag.getBoolean("Active");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putBoolean("Active", this.active);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(getCachedState(), tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }

    @Override
    public void notify(LivingEntity from) {
        damage++;

        if(world != null) {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0f, -5.0f);
        }

        if(damage > MAX_DAMAGE) {
            this.active = false;
        }
    }

    public interface MobSupplier {
        LivingEntity create(ServerWorld world, BlockPos pos, PlayerEntity target);
    }
}
