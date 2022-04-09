package draylar.intotheomega.entity.block;

import draylar.intotheomega.api.BlockEntitySyncing;
import draylar.intotheomega.entity.enigma.EnigmaKingEntity;
import draylar.intotheomega.registry.OmegaBlockEntities;
import draylar.intotheomega.registry.OmegaEntities;
import draylar.intotheomega.registry.OmegaParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnigmaStandBlockEntity extends BlockEntity implements BlockEntitySyncing {

    private static final String ACTIVATED_KEY = "Activated";
    private static final String ACTIVATION_TICKS_KEY = "ActivationTicks";

    private boolean activated = false;
    private int activationTicks = 0;

    public EnigmaStandBlockEntity(BlockPos pos, BlockState state) {
        super(OmegaBlockEntities.ENIGMA_STAND, pos, state);
    }

    public void activate() {
        activated = true;
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, EnigmaStandBlockEntity stand) {

        // Ticking boss spawn animation.
        if(stand.activated) {
            if(stand.activationTicks == 75) {
                ((ServerWorld) world).spawnParticles(
                        OmegaParticles.OMEGA_PARTICLE,
                        stand.getPos().getX() + .5,
                        stand.getPos().getY(),
                        stand.getPos().getZ() + .5,
                        75,
                        .2,
                        .2,
                        .2,
                        0.5
                );

                ((ServerWorld) world).spawnParticles(
                        ParticleTypes.ENCHANTED_HIT,
                        stand.getPos().getX() + .5,
                        stand.getPos().getY(),
                        stand.getPos().getZ() + .5,
                        75,
                        .2,
                        .2,
                        .2,
                        0.5
                );

                ((ServerWorld) world).spawnParticles(
                        ParticleTypes.ENCHANT,
                        stand.getPos().getX() + .5,
                        stand.getPos().getY(),
                        stand.getPos().getZ() + .5,
                        75,
                        .2,
                        .2,
                        .2,
                        0.5
                );
            }

            // At 5 seconds, remove this Enigma Stand.
            if(stand.activationTicks >= 80) {
                ((ServerWorld) world).spawnParticles(
                        OmegaParticles.DARK,
                        stand.getPos().getX() + .5,
                        stand.getPos().getY(),
                        stand.getPos().getZ() + .5,
                        50,
                        .2,
                        .2,
                        .2,
                        0.5
                );

                world.setBlockState(stand.pos, Blocks.AIR.getDefaultState(), 3);
                stand.markRemoved();

                // entity
                EnigmaKingEntity enigmaKing = new EnigmaKingEntity(OmegaEntities.ENIGMA_KING, world);
                enigmaKing.requestTeleport(stand.pos.getX() + .5f, stand.pos.getY(), stand.pos.getZ() + .5f);
                world.spawnEntity(enigmaKing);
                return;
            }

            // sound FX
            if(stand.activationTicks == 1) {
                world.playSound(null, stand.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else if (stand.activationTicks == 20) {
                world.playSound(null, stand.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 0.5f);
            } else if (stand.activationTicks == 40) {
                world.playSound(null, stand.getPos(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, 1.0f, 0.5f);
            } else if (stand.activationTicks == 75) {
                world.playSound(null, stand.getPos(), SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT, SoundCategory.BLOCKS, 0.5f, 0.5f);
            }


            stand.activationTicks++;
            stand.sync();
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean(ACTIVATED_KEY, activated);
        nbt.putInt(ACTIVATION_TICKS_KEY, activationTicks);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.activated = nbt.getBoolean(ACTIVATED_KEY);
        this.activationTicks = nbt.getInt(ACTIVATION_TICKS_KEY);
    }

    public int getActivationTicks() {
        return activationTicks;
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
