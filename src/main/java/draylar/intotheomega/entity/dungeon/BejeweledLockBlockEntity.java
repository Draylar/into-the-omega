package draylar.intotheomega.entity.dungeon;

import draylar.intotheomega.api.block.BlockEntityNotifiable;
import draylar.intotheomega.api.block.BlockEntitySyncing;
import draylar.intotheomega.api.EntityDeathNotifier;
import draylar.intotheomega.registry.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BejeweledLockBlockEntity extends BlockEntity implements BlockEntitySyncing, BlockEntityNotifiable {

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

    public BejeweledLockBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.BEJEWELED_LOCK, pos, state);
    }

    public static <E extends BlockEntity> void serverTick(World world, BlockPos blockPos, BlockState state, BejeweledLockBlockEntity lock) {
        lock.age++;

        // Tick the unlock counter.
        if (lock.unlockTicks >= 0) {
            lock.sync();

            // spawn particles
            for(Direction direction : Direction.values()) {
                if(!direction.getAxis().equals(Direction.Axis.Y)) {
                    BlockPos origin = lock.pos.offset(direction, 5);

                    // go up
                    for(int i = 0; i < 10; i++) {
                        ((ServerWorld) world).spawnParticles(OmegaParticles.SMALL_BLUE_OMEGA_BURST, origin.getX() + .5, origin.getY() + i, origin.getZ() + .5, 1, 0, 1, 0, 0);
                    }
                }
            }

            // break blocks
            if(lock.unlockTicks == 0) {
                // replace bejeweled obsidian 2 blocks up
                world.setBlockState(lock.pos.up(), Blocks.AIR.getDefaultState());
                world.setBlockState(lock.pos.up(2), Blocks.AIR.getDefaultState());

                for(Direction direction : Arrays.stream(Direction.values()).filter(direction -> !direction.getAxis().equals(Direction.Axis.Y)).toList()) {
                    world.setBlockState(lock.pos.offset(direction).down(), Blocks.AIR.getDefaultState());
                    world.setBlockState(lock.pos.offset(direction).offset(direction.rotateYClockwise()).down(), Blocks.AIR.getDefaultState());
                }
            }

            // play grinding sound
            if(lock.unlockTicks % 2 == 0) {
                world.playSound(null, lock.pos.getX(), lock.pos.getY(), lock.pos.getZ(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0f, -5.0f);
            }

            lock.unlockTicks++;
        }

        // If the counter is >= 100, remove this lock.
        if (lock.unlockTicks > 360) {
            world.setBlockState(lock.pos, OmegaBlocks.OBSIDIAN_PILLAR.getDefaultState());
            world.setBlockState(lock.pos.up(), OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState());
            world.setBlockState(lock.pos.up(2), OmegaBlocks.BEJEWELED_OBSIDIAN.getDefaultState());
            lock.markRemoved();
        }

        List<PlayerEntity> players = new ArrayList<>(world.getEntitiesByClass(PlayerEntity.class, new Box(lock.getPos().add(-16, 0, -16), lock.getPos().add(16, 16, 16)), player -> !player.isSpectator()));
        players.forEach(player -> player.addStatusEffect(new StatusEffectInstance(OmegaStatusEffects.DUNGEON_LOCK, 15, 0, true, false)));

        // Summon mobs around the pedestal if a player is nearby.
        if(lock.age % 20 == 0 && !(lock.unlockTicks >= 0)) {
            if(world.random.nextInt(3) == 0) {
                // Ensure a player is nearby before spawning.
                if(!players.isEmpty()) {
                    // Next, ensure we have not hit the local mob-cap.
                    long nearby = world.getEntitiesByClass(HostileEntity.class, new Box(lock.getPos().add(-16, 0, -16), lock.getPos().add(16, 16, 16)), hostile -> true).size();
                    if(nearby < 12) {
                        // Requirements have been hit. Spawn in a random mob and tell it to notify us when it dies.

                        // Attempt to find a random position nearby to spawn our mob at
                        BlockPos spawnPos = null;
                        for(int i = 0; i < 10; i++) {
                            BlockPos potentialPos = lock.getPos().add(world.random.nextInt(32) - 16, world.random.nextInt(5), world.random.nextInt(32) - 16);
                            if(world.getBlockState(potentialPos).isAir()) {
                                spawnPos = potentialPos;
                                break;
                            }
                        }

                        // if a proper spawn position was found, add in our entity
                        if(spawnPos != null) {
                            MobSupplier found = MOBS.get(world.random.nextInt(MOBS.size()));
                            LivingEntity created = found.create(world, spawnPos, players.get(world.random.nextInt(players.size())));
                            ((EntityDeathNotifier) created).setTarget(world.getRegistryKey(), lock.pos);
                            world.spawnEntity(created);
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
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.unlockTicks = nbt.getInt("UnlockTicks");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("UnlockTicks", unlockTicks);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
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
