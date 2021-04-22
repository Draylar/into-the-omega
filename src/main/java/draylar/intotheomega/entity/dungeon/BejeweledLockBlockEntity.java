package draylar.intotheomega.entity.dungeon;

import draylar.intotheomega.api.BlockEntityNotifiable;
import draylar.intotheomega.api.EntityDeathNotifier;
import draylar.intotheomega.registry.OmegaBlocks;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaParticles;
import draylar.intotheomega.registry.OmegaStatusEffects;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BejeweledLockBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable, BlockEntityNotifiable {

    private static final int MAX_DAMAGE = 25;
    private static final List<MobSupplier> MOBS = new ArrayList<>();

    static {
        MOBS.add((world, pos, player) -> {
            EndermanEntity enderman = new EndermanEntity(EntityType.ENDERMAN, world);
            enderman.updatePosition(pos.getX(), pos.getY(), pos.getZ());
            enderman.setTarget(player);
            enderman.setAngryAt(player.getUuid());
            return enderman;
        });
    }

    private int damage = 0;
    private int unlockTicks = -1;
    private int age;

    public BejeweledLockBlockEntity() {
        super(OmegaEntities.BEJEWELED_LOCK);
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

            // Tick the unlock counter.
            if (unlockTicks >= 0) {
                sync();

                // spawn particles
                for(Direction direction : Direction.values()) {
                    if(!direction.getAxis().equals(Direction.Axis.Y)) {
                        BlockPos origin = pos.offset(direction, 5);

                        // go up
                        for(int i = 0; i < 10; i++) {
                            ((ServerWorld) world).spawnParticles(OmegaParticles.SMALL_BLUE_OMEGA_BURST, origin.getX() + .5, origin.getY() + i, origin.getZ() + .5, 1, 0, 1, 0, 0);
                        }
                    }
                }

                // break blocks
                if(unlockTicks == 0) {
                    // replace bejeweled obsidian 2 blocks up
                    world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
                    world.setBlockState(pos.up(2), Blocks.AIR.getDefaultState());

                    for(Direction direction : Arrays.stream(Direction.values()).filter(direction -> !direction.getAxis().equals(Direction.Axis.Y)).collect(Collectors.toList())) {
                        world.setBlockState(pos.offset(direction).down(), Blocks.AIR.getDefaultState());
                        world.setBlockState(pos.offset(direction).offset(direction.rotateYClockwise()).down(), Blocks.AIR.getDefaultState());
                    }
                }

                // play grinding sound
                if(unlockTicks % 2 == 0) {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0f, -5.0f);
                }

                unlockTicks++;
            }

            // If the counter is >= 100, remove this lock.
            if (unlockTicks > 360) {
                world.setBlockState(pos, OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState());
                world.setBlockState(pos.up(), OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState());
                world.setBlockState(pos.up(2), OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState());
                markRemoved();
            }

            List<PlayerEntity> players = new ArrayList<>(world.getEntitiesByClass(PlayerEntity.class, new Box(getPos().add(-16, 0, -16), getPos().add(16, 16, 16)), player -> !player.isSpectator()));
            players.forEach(player -> player.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.DUNGEON_LOCK, 15, 0, true, false)));

            // Summon mobs around the pedestal if a player is nearby.
            if(age % 20 == 0 && !(unlockTicks >= 0)) {
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
                                LivingEntity created = found.create(world, spawnPos, players.get(world.random.nextInt(players.size())));
                                ((EntityDeathNotifier) created).setTarget(world.getRegistryKey(), pos);
                                world.spawnEntity(created);
                            }
                        }
                    }
                }
            }
        }
    }

    public void unlock() {
        unlockTicks = 0;
    }

    public int getUnlockTicks() {
        return Math.max(0, unlockTicks);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.unlockTicks = tag.getInt("UnlockTicks");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("UnlockTicks", unlockTicks);
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
            unlock();
        }
    }

    public interface MobSupplier {
        LivingEntity create(World world, BlockPos pos, PlayerEntity target);
    }
}
