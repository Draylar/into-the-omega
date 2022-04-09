package draylar.intotheomega.mixin.world;

import com.mojang.serialization.Codec;
import draylar.intotheomega.api.OpenSimplex2F;
import draylar.intotheomega.registry.OmegaBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Iterator;
import java.util.Random;

@Mixin(EndSpikeFeature.class)
public abstract class EndPillarDesignMixin extends Feature<EndSpikeFeatureConfig> {

    // TODO: SEED THIS

    @Unique
    private static final OpenSimplex2F noise = new OpenSimplex2F(0);

    public EndPillarDesignMixin(Codec<EndSpikeFeatureConfig> configCodec) {
        super(configCodec);
    }

    /**
     * @author Draylar
     * @reason complete overhaul of the End Pillar generation
     */
    @Overwrite
    private void generateSpike(ServerWorldAccess world, Random random, EndSpikeFeatureConfig config, EndSpikeFeature.Spike spike) {
        int radius = spike.getRadius();
        int staircaseRadius = radius + 3;

        // build staircase
        for(int i = 35; i < spike.getHeight(); i++) {
            int actualStaircaseRadius = staircaseRadius + -(i / 11) + 9;
            for(double z = 0; z <= 1.0; z += 0.1) {
                double xOffset = Math.sin((i + z) / 2f) * actualStaircaseRadius;
                double zOffset = Math.cos((i + z) / 2f) * actualStaircaseRadius;

                Vec3d origin = new Vec3d(spike.getCenterX(), i, spike.getCenterZ());
                Vec3d target = origin.add(xOffset, 0, zOffset);
                double distance = origin.distanceTo(target);
                Vec3d per = target.subtract(origin).multiply(1 / distance);
                for(int m = 0; m < distance; m++) {
                    world.setBlockState(new BlockPos(origin), Blocks.OBSIDIAN.getDefaultState(), 3);
                    origin = origin.add(per);
                }
            }
        }

        int boxRadius = radius * 4;
        for (BlockPos pos : BlockPos.iterate(new BlockPos(spike.getCenterX() - boxRadius, 35, spike.getCenterZ() - boxRadius), new BlockPos(spike.getCenterX() + boxRadius, spike.getHeight() + 10, spike.getCenterZ() + boxRadius))) {
            double distance = pos.getSquaredDistance(spike.getCenterX(), pos.getY(), spike.getCenterZ());
            int practicalRadius = radius + -(pos.getY() / 11) + 9;
            double maxDistance = (practicalRadius * practicalRadius + 1) + noise.noise3_Classic(pos.getX() / 2f, pos.getY() / 2f, pos.getZ() / 2f) * 2;

            if (distance <= maxDistance && pos.getY() < spike.getHeight()) {
                // do not place obsidian above height-2 if the block isn't on an axis
                if(pos.getY() >= spike.getHeight() - 2) {
                    if(spike.getCenterX() - pos.getX() != 0 && spike.getCenterZ() - pos.getZ() != 0) {
                        if(maxDistance - distance <= 2) {
                            // place slabs at the first block
                            if(pos.getY() == spike.getHeight() - 1) {
                                setBlockState(world, pos, OmegaBlocks.OBSIDIAN_SLAB.getDefaultState());
                            }

                            continue;
                        }
                    }
                }

                if (noise.noise3_Classic(pos.getX() / 5f, pos.getY() / 5f, pos.getZ() / 5f) <= -0.2f) {
                    setBlockState(world, pos, Blocks.CRYING_OBSIDIAN.getDefaultState());
                } else {
                    setBlockState(world, pos, Blocks.OBSIDIAN.getDefaultState());
                }

            }
        }

        // Generate the top effects
        int height = spike.getHeight();
        world.setBlockState(new BlockPos(spike.getCenterX() + radius, height, spike.getCenterZ()), Blocks.OBSIDIAN.getDefaultState(), 3);
        world.setBlockState(new BlockPos(spike.getCenterX() - radius, height, spike.getCenterZ()), Blocks.OBSIDIAN.getDefaultState(), 3);
        world.setBlockState(new BlockPos(spike.getCenterX(), height, spike.getCenterZ() + radius), Blocks.OBSIDIAN.getDefaultState(), 3);
        world.setBlockState(new BlockPos(spike.getCenterX(), height, spike.getCenterZ() - radius), Blocks.OBSIDIAN.getDefaultState(), 3);
        world.setBlockState(new BlockPos(spike.getCenterX() + radius, height + 1, spike.getCenterZ()), OmegaBlocks.OBSIDIAN_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST), 3);
        world.setBlockState(new BlockPos(spike.getCenterX() - radius, height + 1, spike.getCenterZ()), OmegaBlocks.OBSIDIAN_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST), 3);
        world.setBlockState(new BlockPos(spike.getCenterX(), height + 1, spike.getCenterZ() + radius), OmegaBlocks.OBSIDIAN_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH), 3);
        world.setBlockState(new BlockPos(spike.getCenterX(), height + 1, spike.getCenterZ() - radius), OmegaBlocks.OBSIDIAN_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH), 3);

        // Place Iron Bars around the End Crystal towards the top of the pillar
        if (spike.isGuarded()) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int m = -2; m <= 2; ++m) {
                for (int n = -2; n <= 2; ++n) {
                    for (int o = 0; o <= 3; ++o) {
                        boolean bl = MathHelper.abs(m) == 2;
                        boolean bl2 = MathHelper.abs(n) == 2;
                        boolean bl3 = o == 3;
                        if (bl || bl2 || bl3) {
                            boolean bl4 = m == -2 || m == 2 || bl3;
                            boolean bl5 = n == -2 || n == 2 || bl3;
                            BlockState blockState = Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, bl4 && n != -2).with(PaneBlock.SOUTH, bl4 && n != 2).with(PaneBlock.WEST, bl5 && m != -2).with(PaneBlock.EAST, bl5 && m != 2);
                            this.setBlockState(world, mutable.set(spike.getCenterX() + m, spike.getHeight() + o, spike.getCenterZ() + n), blockState);
                        }
                    }
                }
            }
        }

        EndCrystalEntity endCrystalEntity = EntityType.END_CRYSTAL.create(world.toServerWorld());
        endCrystalEntity.setBeamTarget(config.getPos());
        endCrystalEntity.setInvulnerable(config.isCrystalInvulnerable());
        endCrystalEntity.refreshPositionAndAngles((double) spike.getCenterX() + 0.5D, (double) (spike.getHeight() + 1), (double) spike.getCenterZ() + 0.5D, random.nextFloat() * 360.0F, 0.0F);
        world.spawnEntity(endCrystalEntity);
        this.setBlockState(world, new BlockPos(spike.getCenterX(), spike.getHeight(), spike.getCenterZ()), Blocks.BEDROCK.getDefaultState());
    }
}
